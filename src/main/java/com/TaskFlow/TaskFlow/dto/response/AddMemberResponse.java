package com.TaskFlow.TaskFlow.dto.response;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddMemberResponse {
    private Long projectId;
    private Long userId;
    private LocalDateTime joinedAt;
    
}
