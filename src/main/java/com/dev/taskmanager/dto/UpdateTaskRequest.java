package com.dev.taskmanager.dto;

import org.openapitools.jackson.nullable.JsonNullable;

import com.dev.taskmanager.validation.NullOrNotBlank;

public record UpdateTaskRequest(
    @NullOrNotBlank 
    String title,
    JsonNullable<String> description,
    Boolean completed
) {}