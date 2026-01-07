package com.TaskFlow.TaskFlow.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class UpdateProjectRequest {
    @NotBlank(message = "El correo electrónico del propietario no puede estar vacío")
    @Email(message = "Debe ser un correo electrónico válido")
    private String ownerEmail;
    private String projectName;
    private String description;

}
