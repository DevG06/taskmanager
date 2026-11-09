package com.dev.taskmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.dev.taskmanager.dto.CreateTaskRequest;
import com.dev.taskmanager.dto.TaskResponse;
import com.dev.taskmanager.model.Task;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TaskMapper {
    TaskResponse toResponse(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Task toEntity(CreateTaskRequest request);
} 

