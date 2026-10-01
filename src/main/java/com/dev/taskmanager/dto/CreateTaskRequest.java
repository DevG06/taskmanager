package com.dev.taskmanager.dto;

public record CreateTaskRequest(String title, String description, boolean completed) {

}
