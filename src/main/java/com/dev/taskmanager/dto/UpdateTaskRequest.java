package com.dev.taskmanager.dto;

import org.openapitools.jackson.nullable.JsonNullable;

public record UpdateTaskRequest(
    String title,
    JsonNullable<String> description,
    Boolean completed
) {
}