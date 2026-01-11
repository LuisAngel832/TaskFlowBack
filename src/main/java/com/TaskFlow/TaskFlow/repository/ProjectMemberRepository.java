package com.TaskFlow.TaskFlow.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TaskFlow.TaskFlow.entity.ProjectMember;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    Optional<ProjectMember> findByProjectAndUser(Long projectId, Long userId);
    boolean existByProjectIdAndUserId(Long projectId, Long userId);
    boolean existsByProjectIdAndUserEmail(Long projectId, String email);   
}
