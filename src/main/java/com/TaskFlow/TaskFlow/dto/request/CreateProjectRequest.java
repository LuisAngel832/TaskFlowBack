package com.TaskFlow.TaskFlow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Solicitud para crear un nuevo proyecto")
public class CreateProjectRequest {
    
    @NotBlank(message = "El nombre del proyecto no puede estar vacío")
    @Schema(description = "Nombre del proyecto", example = "Mi Proyecto")
    private String projectName;

    @NotBlank(message = "La descripción del proyecto no puede estar vacía")
    @Schema(description = "Descripción detallada del proyecto", example = "Este proyecto es para gestionar tareas del equipo")
    private String description;

    @NotNull(message = "El Id del propietario no puede estar vacío")
    @Schema(description = "ID del usuario propietario del proyecto", example = "1")
    private Long userId;
}
