package com.TaskFlow.TaskFlow.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Respuesta con información de una tarea")
public class TaskResponse {

    @Schema(description = "ID de la tarea", example = "1")
    private Long taskId;

    @Schema(description = "Título de la tarea", example = "Implementar login")
    private String taskName;

    @Schema(description = "Descripción de la tarea", example = "Implementar autenticación JWT")
    private String description;

    @Schema(description = "Estado actual de la tarea", example = "IN_PROGRESS")
    private String status;

    @Schema(description = "Prioridad de la tarea", example = "HIGH")
    private String priority;

    @Schema(description = "Fecha de vencimiento", example = "2026-02-15T10:30:00")
    private String dueDate;

    @Schema(description = "ID del proyecto al que pertenece", example = "1")
    private Long projectId;
}
