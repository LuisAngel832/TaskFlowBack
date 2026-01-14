package com.TaskFlow.TaskFlow.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TaskFlow.TaskFlow.entity.ProjectMember;
import com.TaskFlow.TaskFlow.entity.ProjectMemberId;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {
    Optional<ProjectMember> findByProjectIdAndUserId(Long projectId, Long userId);
    boolean existsByProjectIdAndUserId(Long projectId, Long userId);
    boolean existsByProjectIdAndUserEmail(Long projectId, String email);
}
