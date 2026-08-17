package com.project.tasktracker.model;

import java.time.LocalDate;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// Task model
public class Task {
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Due date is required")
    @FutureOrPresent(message = "Due date cannot be in the past")
    private LocalDate dueDate;

    @NotNull(message = "Priority is required")
    private Priority priority;
    private TaskStatus status;

    // Getters
    public Long getId(){
        return id;
    };

    public String getTitle(){
        return title;
    };

    public String getDescription(){
        return description;
    }

    public LocalDate getDueDate(){
        return dueDate;
    };

    public Priority getPriority(){
        return priority;
    };

    public TaskStatus getStatus(){
        return status;
    };

    // Setters
    public void setId(Long id){
        this.id = id;
    };

    public void setTitle(String title){
        this.title = title;
    };

    public void setDescription(String description){
        this.description = description;
    };

    public void setdueDate(LocalDate dueDate){
        this.dueDate = dueDate;
    };

    public void setPriority (Priority priority){
        this.priority = priority;
    }

    public void setStatus(TaskStatus status){
        this.status = status;
    }
}