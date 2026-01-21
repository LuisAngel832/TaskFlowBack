package com.TaskFlow.TaskFlow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Solicitud para cambiar el estado de una tarea")
public class ChangeStatusRequest {

    @NotNull(message = "El id del usuario no puede estar vacio")
    @Schema(description = "ID del usuario que realiza el cambio", example = "1")
    private Long userId;

    @NotBlank(message = "El status no puede esar vacio")
    @Schema(description = "Nuevo estado de la tarea", example = "IN_PROGRESS", allowableValues = {"TO_DO", "IN_PROGRESS", "DONE"})
    private String status;
}
