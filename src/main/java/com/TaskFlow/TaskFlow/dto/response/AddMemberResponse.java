package com.TaskFlow.TaskFlow.dto.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Respuesta al agregar un miembro a un proyecto")
public class AddMemberResponse {

    @Schema(description = "ID del proyecto", example = "1")
    private Long projectId;

    @Schema(description = "ID del usuario agregado", example = "5")
    private Long userId;

    @Schema(description = "Fecha y hora en que se unió al proyecto", example = "2026-01-19T10:30:00")
    private LocalDateTime joinedAt;
}
