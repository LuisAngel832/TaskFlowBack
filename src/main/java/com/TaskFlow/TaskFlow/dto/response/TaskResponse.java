package com.TaskFlow.TaskFlow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskResponse {
    private Long taskId;
    private String taskName;
    private String description;
    private String status;
    private String priority;
    private String dueDate;
    private Long projectId;

}
