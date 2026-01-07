package com.TaskFlow.TaskFlow.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateProjectRequest {
    
    @NotBlank(message = "El nombre del proyecto no puede estar vacío")
    private String projectName;
    @NotBlank(message = "La descripción del proyecto no puede estar vacía")
    private String description;
    @Email(message = "Debe ser un correo electrónico válido")
    @NotBlank(message = "El correo electrónico del propietario no puede estar vacío")
    private String ownerEmail;
}
