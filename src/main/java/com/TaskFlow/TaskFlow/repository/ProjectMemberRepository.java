package com.TaskFlow.TaskFlow.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.TaskFlow.TaskFlow.entity.ProjectMember;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    
}
