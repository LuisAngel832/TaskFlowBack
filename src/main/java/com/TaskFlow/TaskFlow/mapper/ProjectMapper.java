package com.TaskFlow.TaskFlow.mapper;

import com.TaskFlow.TaskFlow.dto.response.ProjectResponse;
import com.TaskFlow.TaskFlow.entity.Project;

public class ProjectMapper {
    

    public ProjectResponse toResponse(Project project){
        return new ProjectResponse(
            project.getId(),
            project.getName(),
            project.getDescription(),
            project.getOwner().getEmail());
    }
}
