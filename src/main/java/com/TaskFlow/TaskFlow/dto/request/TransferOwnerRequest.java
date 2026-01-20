package com.TaskFlow.TaskFlow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Solicitud para transferir la propiedad de un proyecto")
public class TransferOwnerRequest {

    @NotNull(message = "El id del proyecto no puede esta vacio")
    @Schema(description = "ID del proyecto a transferir", example = "1")
    private Long projectId;

    @Email
    @NotBlank(message = "El email del nuevo Administrador no puede estar vacio")
    @Schema(description = "Email del nuevo propietario del proyecto", example = "newowner@example.com")
    private String newOwnerEmail;
}
