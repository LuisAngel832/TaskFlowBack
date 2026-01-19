package com.TaskFlow.TaskFlow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateProjectRequest {
    
    @NotBlank(message = "El nombre del proyecto no puede estar vacío")
    private String projectName;
    @NotBlank(message = "La descripción del proyecto no puede estar vacía")
    private String description;
    @NotNull(message = "El Id del propietario no puede estar vacío")
    private Long userId;
}
