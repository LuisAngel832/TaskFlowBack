package com.TaskFlow.TaskFlow.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Respuesta al actualizar un proyecto")
public class UpdateProjectResponse {

    @Schema(description = "ID del proyecto actualizado", example = "1")
    private Long id;

    @Schema(description = "Nombre actualizado del proyecto", example = "Proyecto Actualizado")
    private String name;

    @Schema(description = "Descripción actualizada del proyecto", example = "Nueva descripción")
    private String description;
}
