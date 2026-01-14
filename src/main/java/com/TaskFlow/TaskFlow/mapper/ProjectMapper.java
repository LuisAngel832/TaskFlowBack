package com.TaskFlow.TaskFlow.mapper;

import org.springframework.stereotype.Component;

import com.TaskFlow.TaskFlow.dto.response.ProjectResponse;
import com.TaskFlow.TaskFlow.entity.Project;

@Component
public class ProjectMapper {
    

    public ProjectResponse toResponse(Project project){
        return new ProjectResponse(
            project.getId(),
            project.getName(),
            project.getDescription(),
            project.getOwner().getEmail());
    }
}
