package com.TaskFlow.TaskFlow.controller;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TaskFlow.TaskFlow.dto.request.CreateProjectRequest;
import com.TaskFlow.TaskFlow.dto.request.TransferOwnerRequest;
import com.TaskFlow.TaskFlow.dto.request.UpdateProjectRequest;
import com.TaskFlow.TaskFlow.dto.response.AddMemberResponse;
import com.TaskFlow.TaskFlow.dto.response.ProjectResponse;
import com.TaskFlow.TaskFlow.dto.response.UpdateProjectResponse;
import com.TaskFlow.TaskFlow.entity.User;
import com.TaskFlow.TaskFlow.service.ProjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/projects")
@Tag(name = "Proyectos", description = "Operaciones relacionadas con la gestión de proyectos")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }

    @Operation(summary = "Crear un nuevo proyecto", description = "Crea un nuevo proyecto en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Proyecto creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody CreateProjectRequest request, @AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(user.getId(), request));
    }

    @Operation(summary = "Actualizar un proyecto existente", description = "Actualiza los detalles de un proyecto existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Proyecto actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado"),
        @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
    })
    @PutMapping("/{projectId}")
    public ResponseEntity<UpdateProjectResponse> updateProject(@PathVariable Long projectId, @Valid @RequestBody UpdateProjectRequest request, @AuthenticationPrincipal User user) throws AccessDeniedException{
        return ResponseEntity.ok(projectService.updateProject(projectId, user.getEmail(), request));
    }

    @Operation(summary = "Obtener todos los proyectos del usuario autenticado", description = "Retorna una lista de todos los proyectos asociados al usuario actualmente autenticado.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Proyectos obtenidos exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @GetMapping("/all")
    public ResponseEntity<List<ProjectResponse>> getAllProjects(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(projectService.getAllMyProjects(user.getId()));
    }

    @Operation(summary = "Obtener un proyecto por ID", description = "Retorna los detalles de un proyecto específico por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Proyecto obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
    })
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable Long projectId){
        return ResponseEntity.ok(projectService.getProjectById(projectId));
    }

    @Operation(summary = "Eliminar un proyecto", description = "Elimina un proyecto existente del sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Proyecto eliminado exitosamente"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado"),
        @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
    })
    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId, @AuthenticationPrincipal User user) throws AccessDeniedException{
        projectService.deleteProject(projectId, user.getEmail());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Agregar un miembro a un proyecto", description = "Agrega un nuevo miembro a un proyecto existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Miembro agregado exitosamente"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado"),
        @ApiResponse(responseCode = "404", description = "Proyecto o usuario no encontrado")
    })
    @PostMapping("/{projectId}")
    public ResponseEntity<AddMemberResponse> addMember(@PathVariable Long projectId, @AuthenticationPrincipal User user){
        AddMemberResponse response = projectService.addMemberToProject(projectId, user.getId());
        return ResponseEntity.ok(response);
    }

@Operation(summary = "Remover un miembro de un proyecto", description = "Remueve al usuario autenticado de un proyecto existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Miembro removido exitosamente"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado"),
        @ApiResponse(responseCode = "404", description = "Proyecto o miembro no encontrado")
    })
    @DeleteMapping("/{projectId}/")
    public ResponseEntity<?> removeMember(@PathVariable Long projectId, @AuthenticationPrincipal User user) throws AccessDeniedException {
        projectService.removeMemberFromProject(projectId, user.getId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Transferir propiedad de un proyecto", description = "Transfiere la propiedad de un proyecto a otro usuario miembro.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Propiedad transferida exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "403", description = "Solo el propietario puede transferir el proyecto"),
        @ApiResponse(responseCode = "404", description = "Proyecto o nuevo propietario no encontrado")
    })
    @PutMapping("/transfer-project")
    public ResponseEntity<ProjectResponse> transferOwner(@Valid @RequestBody TransferOwnerRequest request, @AuthenticationPrincipal User user) throws AccessDeniedException {
        return ResponseEntity.ok(projectService.transferOwnership(request, user.getEmail()));
    }
    

}
