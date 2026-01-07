package com.TaskFlow.TaskFlow.service;

import java.time.LocalDateTime;

import com.TaskFlow.TaskFlow.dto.request.CreateProjectRequest;
import com.TaskFlow.TaskFlow.dto.request.UpdateProjectRequest;
import com.TaskFlow.TaskFlow.dto.response.CreateProyectResponse;
import com.TaskFlow.TaskFlow.dto.response.UpdateProjectResponse;
import com.TaskFlow.TaskFlow.entity.Project;
import com.TaskFlow.TaskFlow.entity.ProjectMember;
import com.TaskFlow.TaskFlow.entity.User;
import com.TaskFlow.TaskFlow.repository.UserRepository;
import com.TaskFlow.TaskFlow.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

import jakarta.transaction.Transactional;


@Service
public class ProjectService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public ProjectService(UserRepository userRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }
    
    @Transactional
    public CreateProyectResponse createProject(CreateProjectRequest createProjectRequest) {
        User owner = userRepository.findByEmail(createProjectRequest.getOwnerEmail())
        .orElseThrow(()-> new RuntimeException("El propietario del proyecto no existe"));

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
    public UpdateProjectResponse updateProject(Long projectId, UpdateProjectRequest projectRequest) {
        Project project = projectRepository.findById(projectId)
        .orElseThrow(()-> new RuntimeException("El proyecto no existe"));

        //esto lo validaremos mas fuerte con spring security
        if(!project.getOwner().getEmail().equals(projectRequest.getOwnerEmail())){
            throw new RuntimeException("No tienes permisos para actualizar este proyecto");
        }

        Optional.ofNullable(projectRequest.getProjectName()).ifPresent(project::setName);
        Optional.ofNullable(projectRequest.getDescription()).ifPresent(project::setDescription);
        
        Project updatedProject = projectRepository.save(project);
        return new UpdateProjectResponse(
            updatedProject.getId(),
            updatedProject.getName(),
            updatedProject.getDescription()
        );
    }

}
