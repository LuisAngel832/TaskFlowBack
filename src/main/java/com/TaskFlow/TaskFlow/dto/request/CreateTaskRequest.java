package com.TaskFlow.TaskFlow.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CreateTaskRequest {

    @NotNull(message = "El ID del proyecto no puede estar vacio")
    private Long projectId;


    @Email(message = "El correo electrónico no es válido")
    private String assigneeEmail;

    @NotBlank(message = "El título de la tarea no puede estar vacío")
    private String title;

    private String description;

    @NotBlank(message = "Tienes que especificar la prioridad de la tarea")
    private String priority;

    @NotNull(message = "La fecha de vencimiento no puede estar vacía")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dueDate;


    
}
