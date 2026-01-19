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

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/tasks")
public class TaskController {
    
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request, @AuthenticationPrincipal User user) throws AccessDeniedException {
        TaskResponse response = taskService.createTask(request, user.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@Valid @RequestBody UpdateTaskRequest request, @AuthenticationPrincipal User user, @PathVariable Long taskId) throws AccessDeniedException {
        TaskResponse response =taskService.updateTask(taskId, request,user.getId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId, @AuthenticationPrincipal User user) throws AccessDeniedException {
        taskService.delteTask(taskId,user.getId() );
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/status/{taskId}")
    public ResponseEntity<TaskResponse> changeStatusTask(@PathVariable Long taskId,@Valid @RequestBody ChangeStatusRequest request) {
        TaskResponse response = taskService.changeStatus(taskId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllMyTask(@AuthenticationPrincipal User user){
        List<TaskResponse> responses = taskService.getAllMyTask(user.getId());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<TaskResponse> assignTask(@PathVariable Long taskId, @PathVariable Long userId) throws AccessDeniedException {
        TaskResponse response = taskService.assignedTask(userId, taskId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{taskId}/unassign/{userId}")
    public ResponseEntity<TaskResponse> dassignedTask(@PathVariable Long taskId, @PathVariable Long userId) throws AccessDeniedException {
        TaskResponse response = taskService.dassignedTask(taskId, userId);
        return ResponseEntity.ok(response);
    }

    
    


}
