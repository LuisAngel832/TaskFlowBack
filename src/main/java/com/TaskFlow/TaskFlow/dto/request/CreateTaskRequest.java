package com.TaskFlow.TaskFlow.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Solicitud para crear una nueva tarea")
public class CreateTaskRequest {

    @NotNull(message = "El ID del proyecto no puede estar vacio")
    @Schema(description = "ID del proyecto al que pertenece la tarea", example = "1")
    private Long projectId;

    @Email(message = "El correo electrónico no es válido")
    @Schema(description = "Email del usuario a asignar (opcional)", example = "user@example.com")
    private String assigneeEmail;

    @NotBlank(message = "El título de la tarea no puede estar vacío")
    @Schema(description = "Título de la tarea", example = "Implementar feature de login")
    private String title;

    @Schema(description = "Descripción detallada de la tarea", example = "Implementar autenticación con JWT")
    private String description;

    @NotBlank(message = "Tienes que especificar la prioridad de la tarea")
    @Schema(description = "Prioridad de la tarea", example = "HIGH", allowableValues = {"LOW", "MEDIUM", "HIGH"})
    private String priority;

    @NotNull(message = "La fecha de vencimiento no puede estar vacía")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Fecha de vencimiento de la tarea", example = "2026-02-15T10:30:00")
    private LocalDateTime dueDate;
}
