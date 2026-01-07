package com.TaskFlow.TaskFlow.entity;

import java.io.Serializable;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ProjectMemberId implements Serializable {
    private Long project;
    private Long user;
    
}
