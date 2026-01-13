package com.TaskFlow.TaskFlow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.TaskFlow.TaskFlow.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    List<Project> findByNameContainingIgnoreCase(String name);

    List<Project> findByStatus(@Param("status") String status);

    Optional<Project> findByIdAndOwnerEmail(Long id);

    @Query("SELECT p FROM Project p JOIN p.members m WHERE m.user.id = :userId OR p.owner.id = :userId")
    List<Project> findAllByUserIdInvolved(Long userId);
}
