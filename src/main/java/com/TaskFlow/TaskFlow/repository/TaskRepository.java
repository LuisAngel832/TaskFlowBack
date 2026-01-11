package com.TaskFlow.TaskFlow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.TaskFlow.TaskFlow.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    
    @Query("SELECT t FROM Task t " +
           "JOIN t.project p " +
           "LEFT JOIN p.members m " +
           "WHERE t.assignedTo.id = :userId " +
           "OR p.owner.id = :userId " +
           "OR m.user.id = :userId")
    List<Task> findAllTasksForUser(@Param("userId") Long userId);;
}
