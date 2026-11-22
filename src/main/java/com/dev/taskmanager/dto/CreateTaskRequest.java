package com.dev.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTaskRequest(
    @NotBlank(message = "Title cannot be blank")
    String title,
    String description, 
    boolean completed
) {}
