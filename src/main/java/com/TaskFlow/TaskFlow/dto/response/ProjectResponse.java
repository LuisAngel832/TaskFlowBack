package com.TaskFlow.TaskFlow.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Respuesta con información de un proyecto")
public class ProjectResponse {

    @Schema(description = "ID del proyecto", example = "1")
    private Long id;

    @Schema(description = "Nombre del proyecto", example = "Mi Proyecto")
    private String projectName;

    @Schema(description = "Descripción del proyecto", example = "Proyecto para gestión de tareas")
    private String description;

    @Schema(description = "Email del propietario del proyecto", example = "owner@example.com")
    private String ownerEmail;
}
