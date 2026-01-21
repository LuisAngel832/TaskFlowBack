package com.TaskFlow.TaskFlow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Solicitud para actualizar un proyecto existente")
public class UpdateProjectRequest {

    @NotBlank(message = "El correo electrónico del propietario no puede estar vacío")
    @Email(message = "Debe ser un correo electrónico válido")
    @Schema(description = "Email del propietario del proyecto para validación", example = "owner@example.com")
    private String ownerEmail;

    @Schema(description = "Nuevo nombre del proyecto (opcional)", example = "Proyecto Actualizado")
    private String projectName;

    @Schema(description = "Nueva descripción del proyecto (opcional)", example = "Descripción actualizada")
    private String description;
}
