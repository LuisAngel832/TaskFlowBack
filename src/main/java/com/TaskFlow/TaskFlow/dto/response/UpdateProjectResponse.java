package com.TaskFlow.TaskFlow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class UpdateProjectResponse {
    private Long id;
    private String name;
    private String description;
}
