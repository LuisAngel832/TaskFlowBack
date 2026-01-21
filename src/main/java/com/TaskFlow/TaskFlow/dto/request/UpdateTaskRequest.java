package com.TaskFlow.TaskFlow.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Solicitud para actualizar una tarea existente")
public class UpdateTaskRequest {

    @Schema(description = "Nuevo título de la tarea", example = "Título actualizado")
    private String title;

    @Schema(description = "Nueva descripción de la tarea", example = "Descripción actualizada")
    private String description;

    @Schema(description = "Nuevo estado de la tarea", example = "IN_PROGRESS", allowableValues = {"TO_DO", "IN_PROGRESS", "DONE"})
    private String status;

    @Schema(description = "Nueva prioridad de la tarea", example = "MEDIUM", allowableValues = {"LOW", "MEDIUM", "HIGH"})
    private String priority;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Nueva fecha de vencimiento", example = "2026-03-01 14:00:00")
    private LocalDateTime dueDate;
}
