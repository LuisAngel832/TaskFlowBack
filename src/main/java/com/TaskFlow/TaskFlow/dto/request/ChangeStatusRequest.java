package com.TaskFlow.TaskFlow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ChangeStatusRequest {
    @NotNull(message = "El id del usuario no puede estar vacio")
    private Long userId;

    @NotBlank(message = "El status no puede esar vacio")
    private String status;
    
}
