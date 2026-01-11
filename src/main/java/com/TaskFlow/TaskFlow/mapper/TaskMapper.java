package com.TaskFlow.TaskFlow.mapper;

import org.springframework.stereotype.Component;
import com.TaskFlow.TaskFlow.entity.Task;
import com.TaskFlow.TaskFlow.entity.Project;

import com.TaskFlow.TaskFlow.dto.request.CreateTaskRequest;
import com.TaskFlow.TaskFlow.dto.response.TaskResponse;

@Component
public class TaskMapper {
    
    public Task toEntity(CreateTaskRequest request, Project project) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setProject(project);
        return task;
    }

    public TaskResponse toResponse(Task task) {
        return new TaskResponse(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getStatus().name(),
            task.getPriority().name(),
            task.getDueDate().toString(),
            task.getProject().getId()
        );
    }
}
