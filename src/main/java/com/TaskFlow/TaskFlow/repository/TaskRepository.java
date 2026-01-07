package com.TaskFlow.TaskFlow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TaskFlow.TaskFlow.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    
}
