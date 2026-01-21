package com.TaskFlow.TaskFlow.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TaskFlow.TaskFlow.dto.request.ChangeStatusRequest;
import com.TaskFlow.TaskFlow.dto.request.CreateTaskRequest;
import com.TaskFlow.TaskFlow.dto.request.UpdateTaskRequest;
import com.TaskFlow.TaskFlow.dto.response.TaskResponse;
import com.TaskFlow.TaskFlow.entity.User;
import com.TaskFlow.TaskFlow.exception.AccessDeniedException;
import com.TaskFlow.TaskFlow.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/tasks")
@Tag(name = "Tareas", description = "Operaciones relacionadas con la gestión de tareas")
public class TaskController {
    
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Crear una nueva tarea", description = "Crea una nueva tarea dentro de un proyecto específico.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tarea creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado al proyecto"),
        @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
    })
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request, @AuthenticationPrincipal User user) throws AccessDeniedException {
        TaskResponse response = taskService.createTask(request, user.getId());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Actualizar una tarea", description = "Actualiza los detalles de una tarea existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tarea actualizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado"),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@Valid @RequestBody UpdateTaskRequest request, @AuthenticationPrincipal User user, @Parameter(description = "ID de la tarea a actualizar") @PathVariable Long taskId) throws AccessDeniedException {
        TaskResponse response =taskService.updateTask(taskId, request,user.getId());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar una tarea", description = "Elimina una tarea existente del sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Tarea eliminada exitosamente"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado"),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@Parameter(description = "ID de la tarea a eliminar") @PathVariable Long taskId, @AuthenticationPrincipal User user) throws AccessDeniedException {
        taskService.delteTask(taskId,user.getId() );
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cambiar el estado de una tarea", description = "Actualiza únicamente el estado de una tarea (TO_DO, IN_PROGRESS, DONE).")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Estado inválido"),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    @PatchMapping("/status/{taskId}")
    public ResponseEntity<TaskResponse> changeStatusTask(@Parameter(description = "ID de la tarea") @PathVariable Long taskId, @Valid @RequestBody ChangeStatusRequest request) {
        TaskResponse response = taskService.changeStatus(taskId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener todas mis tareas", description = "Retorna todas las tareas asignadas al usuario autenticado.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tareas obtenidas exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllMyTask(@AuthenticationPrincipal User user) {
        List<TaskResponse> responses = taskService.getAllMyTask(user.getId());
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Asignar tarea a un usuario", description = "Asigna una tarea existente a un miembro del proyecto.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tarea asignada exitosamente"),
        @ApiResponse(responseCode = "403", description = "El usuario no es miembro del proyecto"),
        @ApiResponse(responseCode = "404", description = "Tarea o usuario no encontrado")
    })
    @PutMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<TaskResponse> assignTask(@Parameter(description = "ID de la tarea") @PathVariable Long taskId, @Parameter(description = "ID del usuario a asignar") @AuthenticationPrincipal User user) throws AccessDeniedException {
        TaskResponse response = taskService.assignedTask(user.getId(), taskId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Desasignar tarea de un usuario", description = "Remueve la asignación de una tarea de un usuario.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tarea desasignada exitosamente"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado"),
        @ApiResponse(responseCode = "404", description = "Tarea o usuario no encontrado")
    })
    @PutMapping("/{taskId}/unassign/{userId}")
    public ResponseEntity<TaskResponse> dassignedTask(@Parameter(description = "ID de la tarea") @PathVariable Long taskId, @Parameter(description = "ID del usuario a desasignar") @AuthenticationPrincipal User user) throws AccessDeniedException {
        TaskResponse response = taskService.dassignedTask(taskId, user.getId());
        return ResponseEntity.ok(response);
    }

    
    


}
