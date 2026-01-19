package com.TaskFlow.TaskFlow.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task extends Auditable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "El título de la tarea no puede estar vacío")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "El estado de la tarea no puede estar vacío")
    @Enumerated(EnumType.STRING)
    private EnumTaskStatus status = EnumTaskStatus.TO_DO;

    @Column(nullable = false)
    @NotNull(message = "La prioridad de la tarea no puede estar vacía")
    @Enumerated(EnumType.STRING)
    private EnumPriorityTask priority;

    @Column(name = "due_date", nullable = false)
    @NotNull(message = "La fecha de vencimiento no puede estar vacía")
    private LocalDateTime dueDate;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    @NotNull(message = "El proyecto asociado no puede estar vacío")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private User assignedTo;
    
}
