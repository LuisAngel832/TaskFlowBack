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

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody CreateProjectRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(request));
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<UpdateProjectResponse> updateProject(@PathVariable Long projectId, @Valid @RequestBody UpdateProjectRequest request) throws AccessDeniedException{
        return ResponseEntity.ok(projectService.updateProject(projectId, request));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectResponse>> getAllProjects(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(projectService.getAllMyProjects(user.getId()));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable Long projectId){
        return ResponseEntity.ok(projectService.getProjectById(projectId));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId, @AuthenticationPrincipal User user) throws AccessDeniedException{
        projectService.deleteProject(projectId, user.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{projectId}")
    public ResponseEntity<AddMemberResponse> addMember(@PathVariable Long projectId, @AuthenticationPrincipal User user){
        AddMemberResponse response = projectService.addMemberToProject(projectId, user.getId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{projectId}/")
    public ResponseEntity<?> removeMember( @PathVariable Long projectId,@AuthenticationPrincipal User user) throws AccessDeniedException{
        projectService.removeMemberFromProject(projectId, user.getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/transfer-project")
    public ResponseEntity<ProjectResponse> transferOwner(@Valid @RequestBody TransferOwnerRequest request, @AuthenticationPrincipal User user) throws AccessDeniedException{
        return ResponseEntity.ok(projectService.transferOwnership(request, user.getEmail()));
    }
    

}
