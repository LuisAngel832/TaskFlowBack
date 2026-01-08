package com.TaskFlow.TaskFlow.service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

import com.TaskFlow.TaskFlow.dto.request.CreateProjectRequest;
import com.TaskFlow.TaskFlow.dto.request.UpdateProjectRequest;
import com.TaskFlow.TaskFlow.dto.response.AddMemberResponse;
import com.TaskFlow.TaskFlow.dto.response.CreateProyectResponse;
import com.TaskFlow.TaskFlow.dto.response.UpdateProjectResponse;
import com.TaskFlow.TaskFlow.entity.Project;
import com.TaskFlow.TaskFlow.entity.ProjectMember;
import com.TaskFlow.TaskFlow.entity.User;
import com.TaskFlow.TaskFlow.exception.ResourceNotFoundException;
import com.TaskFlow.TaskFlow.repository.UserRepository;
import com.TaskFlow.TaskFlow.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.TaskFlow.TaskFlow.repository.ProjectMemberRepository;

import jakarta.transaction.Transactional;

@Service
public class ProjectService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public ProjectService(UserRepository userRepository,
            ProjectRepository projectRepository,
            ProjectMemberRepository projectMemberRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.projectMemberRepository = projectMemberRepository;
    }

    @Transactional
    public CreateProyectResponse createProject(CreateProjectRequest createProjectRequest) {
        User owner = userRepository.findByEmail(createProjectRequest.getOwnerEmail())
                .orElseThrow(() -> new RuntimeException("El propietario del proyecto no existe"));

        Project project = new Project();
        project.setName(createProjectRequest.getProjectName());
        project.setDescription(createProjectRequest.getDescription());
        project.setOwner(owner);

        Project projectSaved = projectRepository.save(project);

        ProjectMember member = new ProjectMember();
        member.setProject(projectSaved);
        member.setUser(owner);
        member.setJoinedAt(LocalDateTime.now());

        return new CreateProyectResponse(
                projectSaved.getId(),
                projectSaved.getName(),
                projectSaved.getDescription(),
                projectSaved.getOwner().getEmail());
    }

    @Transactional
    public UpdateProjectResponse updateProject(Long projectId, UpdateProjectRequest projectRequest)
            throws AccessDeniedException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("El proyecto no existe"));

        // esto lo validaremos mas fuerte con spring security
        if (!project.getOwner().getEmail().equals(projectRequest.getOwnerEmail())) {
            throw new AccessDeniedException("No tienes permisos para actualizar este proyecto");
        }

        Optional.ofNullable(projectRequest.getProjectName()).ifPresent(project::setName);
        Optional.ofNullable(projectRequest.getDescription()).ifPresent(project::setDescription);

        Project updatedProject = projectRepository.save(project);
        return new UpdateProjectResponse(
                updatedProject.getId(),
                updatedProject.getName(),
                updatedProject.getDescription());
    }

    @Transactional
    public void deleteProject(Long projectId, String ownerEmail) throws AccessDeniedException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("El proyecto no existe"));

        // esto lo validaremos mas fuerte con spring security
        if (!project.getOwner().getEmail().equals(ownerEmail)) {
            throw new AccessDeniedException("No tienes permisos para eliminar este proyecto");
        }
        projectRepository.delete(project);
    }

    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("El proyecto no existe"));
    }

    // en este metodo falta validar que el userId corresponda al usuario
    // autenticado, eso lo aremos con spring security
    public List<Project> getAllMyProjects(Long userId) {
        return projectRepository.findAllByUserIdInvolved(userId);
    }

    @Transactional
    public AddMemberResponse addMemberToProject(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("El proyecto no existe"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe"));

        ProjectMember member = new ProjectMember();
        member.setProject(project);
        member.setUser(user);
        member.setJoinedAt(LocalDateTime.now());

        projectMemberRepository.save(member);

        return new AddMemberResponse(
                project.getId(),
                user.getId(),
                member.getJoinedAt());
    }

    @Transactional
    public void removeMemberFromProject(Long projectId, Long userId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("El proyecto no existe");
        }

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("El usuario no existe");
        }

        ProjectMember member = projectMemberRepository.findByProjectAndUser(projectId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("El miembro no existe en el proyecto"));

        projectMemberRepository.delete(member);
    }

    @Transactional
    public void transferOwnership(Long projectId, String currentOwnerEmail, String newOwnerEmail) throws AccessDeniedException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado"));

        
        if (!project.getOwner().getEmail().equals(currentOwnerEmail)) {
            throw new AccessDeniedException("Solo el propietario puede transferir el proyecto");
        }

        User newOwner = userRepository.findByEmail(newOwnerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("El nuevo propietario no existe"));

        project.setOwner(newOwner);
        projectRepository.save(project);
    }
}
