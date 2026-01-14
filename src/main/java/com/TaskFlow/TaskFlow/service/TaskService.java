package com.TaskFlow.TaskFlow.service;

import com.TaskFlow.TaskFlow.dto.request.ChangeStatusRequest;
import com.TaskFlow.TaskFlow.dto.request.CreateTaskRequest;
import com.TaskFlow.TaskFlow.dto.request.UpdateTaskRequest;
import com.TaskFlow.TaskFlow.dto.response.TaskResponse;
import com.TaskFlow.TaskFlow.entity.Project;
import com.TaskFlow.TaskFlow.entity.ProjectMember;
import com.TaskFlow.TaskFlow.entity.Task;
import com.TaskFlow.TaskFlow.exception.ResourceNotFoundException;
import com.TaskFlow.TaskFlow.repository.ProjectRepository;
import com.TaskFlow.TaskFlow.repository.TaskRepository;
import com.TaskFlow.TaskFlow.repository.UserRepository;
import com.TaskFlow.TaskFlow.mapper.TaskMapper;
import com.TaskFlow.TaskFlow.entity.EnumPriorityTask;
import com.TaskFlow.TaskFlow.entity.EnumTaskStatus;

import jakarta.transaction.Transactional;

import com.TaskFlow.TaskFlow.repository.ProjectMemberRepository;
import com.TaskFlow.TaskFlow.exception.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

import com.TaskFlow.TaskFlow.entity.User;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final TaskMapper taskMapper;

    public TaskService(ProjectRepository projectRepository,
            TaskRepository taskRepository,
            UserRepository userRepository,
            ProjectMemberRepository projectMemberRepository,
            TaskMapper taskMapper) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectMemberRepository = projectMemberRepository;
        this.taskMapper = taskMapper;
    }

    @Transactional
    public TaskResponse createTask(CreateTaskRequest request) throws AccessDeniedException {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado"));

        validateOwnership(project, request.getUserId());
        validateDueDate(request.getDueDate()); 

 
        Task task =taskMapper.toEntity(request, project);

        if (request.getAssigneeEmail() != null) {
            assignUserToTask(task, project, request.getAssigneeEmail());
        }

        Task taskSaved = taskRepository.save(task);
        return taskMapper.toResponse(taskSaved);
    }


    @Transactional
    public TaskResponse updateTask(Long taskId, UpdateTaskRequest request) throws AccessDeniedException{
        Task task = taskRepository.findById(taskId)
        .orElseThrow(()-> new ResourceNotFoundException("Tarea no encontrada"));

        User user = userRepository.findById(request.getUserId())
        .orElseThrow(()-> new ResourceNotFoundException("Usuario no encontrado"));


        validateOwnership(task.getProject(), user.getId());
        if(request.getDueDate() != null){
            validateDueDate(request.getDueDate(),task);
        }

        Optional.ofNullable(request.getTitle()).ifPresent(task::setTitle);
        Optional.ofNullable(request.getDescription()).ifPresent(task::setDescription);
        Optional.ofNullable(request.getStatus()).ifPresent(status -> task.setStatus(EnumTaskStatus.valueOf(status.toUpperCase())));
        Optional.ofNullable(request.getPriority()).ifPresent(priority -> task.setPriority(EnumPriorityTask.valueOf(priority)));
        Optional.ofNullable(request.getDueDate()).ifPresent(task::setDueDate);

        taskRepository.save(task);

        return taskMapper.toResponse(task);
    }

    @Transactional
    public void delteTask(Long taskId, Long userId) throws AccessDeniedException{
        Task task = taskRepository.findById(taskId)
        .orElseThrow(()-> new ResourceNotFoundException("Tarea no encontrada"));

        User user = userRepository.findById(userId)
        .orElseThrow(()-> new ResourceNotFoundException("Usuario no encontrado"));

        validateOwnership(task.getProject(), user.getId());

        taskRepository.delete(task);

    }

    public TaskResponse changeStatus(Long taskId,ChangeStatusRequest request) throws AccessDeniedException{

        Task task = taskRepository.findById(taskId)
        .orElseThrow(()-> new ResourceNotFoundException("Tarea no encontrada"));

        boolean isMember = projectMemberRepository.existsByProjectIdAndUserId(task.getProject().getId(), request.getUserId());
        boolean isOwner = task.getProject().getOwner().getId().equals(request.getUserId());


        if(!isMember && !isOwner){
            throw new AccessDeniedException("No tienes permiso para cambiar el estado");
        }
        try{
            task.setStatus(EnumTaskStatus.valueOf(request.getStatus().toUpperCase()));
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Estado no valido: " + request.getStatus());
        } 

        taskRepository.save(task);

        return taskMapper.toResponse(task);
    }


    public List<TaskResponse> getAllMyTask(Long userId){
        if(!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("Usuario no encontrado");
        }

        List<Task> listTask = taskRepository.findAllTasksForUser(userId);

        if(listTask.isEmpty()){
            return new ArrayList<>();
        }

        return listTask.stream()
        .map(task -> taskMapper.toResponse(task))
        .collect(Collectors.toList());
    }


    @Transactional
    public TaskResponse assignedTask(Long userId, Long taskId) throws AccessDeniedException{

        Task task = taskRepository.findById(taskId)
        .orElseThrow(()-> new ResourceNotFoundException("Tarea no encontrada"));

        // cuando tengamos spring security tendremos que validar que el usuario que esta haciendo la peticion sea
        //o el admin, o el usuario real

        ProjectMember member = projectMemberRepository.findByProjectIdAndUserId(task.getProject().getId(), userId)
        .orElseThrow(()-> new ResourceNotFoundException("El usuario no es colaborador del proyecto"));

        if(task.getAssignedTo() != null){
            throw new AccessDeniedException("La tarea ya tiene un usuario asignado");
        }

        if(task.getProject()!=member.getProject()){
            throw new IllegalStateException("La tarea no pertenece al proyecto del colaboracor");
        }

        task.setAssignedTo(member.getUser());

        taskRepository.save(task);

        return taskMapper.toResponse(task);
    }

    //aqui tenemos que validar con security que el que solicita la desasignacion sea el mismo  
    // usuario que el que esta asignado en la tarea o el admin
    @Transactional
    public TaskResponse dassignedTask(Long taskId, Long requesterId) throws AccessDeniedException{

        Task task = taskRepository.findById(taskId)
        .orElseThrow(()-> new ResourceNotFoundException("Tarea no encontrada"));

        if(task.getAssignedTo() == null){
            throw new IllegalStateException("No hay ningun usuario asignado");
        }

        if(!userRepository.existsById(requesterId)){
            throw new ResourceNotFoundException("Usuario no encontrado");
        }

        //aqui validaremos el punto de arriba

        if(!task.getAssignedTo().getId().equals(requesterId) && !task.getProject().getOwner().getId().equals(requesterId)){
            throw new AccessDeniedException("No tienes permiso para desasignar esta tarea");
        }

        task.setAssignedTo(null);
        

        return taskMapper.toResponse(taskRepository.save(task));
    }


    private void validateOwnership(Project project, Long userId) throws AccessDeniedException {
        if (!project.getOwner().getId().equals(userId)) {
            throw new AccessDeniedException("No tienes permiso para agregar tareas");
        }
    }

    private void validateDueDate(LocalDateTime dueDate) {
        if (dueDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de vencimiento debe ser futura");
        }
    }

    private void validateDueDate(LocalDateTime dueDate, Task task) {
        if (dueDate.isBefore(task.getCreatedAt())) {
            throw new IllegalArgumentException("La fecha de vencimiento debe ser futura");
        }
    }
    

    private void assignUserToTask(Task task, Project project, String email) {
        if (!projectMemberRepository.existsByProjectIdAndUserEmail(project.getId(), email)) {
            throw new IllegalArgumentException("El usuario asignado no es miembro del proyecto");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        task.setAssignedTo(user);
    }
}

