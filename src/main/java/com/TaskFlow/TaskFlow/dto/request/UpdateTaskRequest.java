package com.TaskFlow.TaskFlow.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateTaskRequest {
    @NotNull(message = "El id del usuario no puede esta vacio")
    private Long userId;

    private String title;

    private String description;

    private String status;

    private String priority;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dueDate;

    
}
