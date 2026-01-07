package com.TaskFlow.TaskFlow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateProyectResponse {
    private Long id;
    private String projectName;
    private String description;
    private String ownerEmail;
}
