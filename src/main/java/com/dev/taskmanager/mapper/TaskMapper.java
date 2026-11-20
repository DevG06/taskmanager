package com.dev.taskmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.dev.taskmanager.dto.CreateTaskRequest;
import com.dev.taskmanager.dto.TaskResponse;
import com.dev.taskmanager.dto.UpdateTaskRequest;
import com.dev.taskmanager.model.Task;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = JsonNullableMapper.class)
public interface TaskMapper {
    TaskResponse toResponse(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Task toEntity(CreateTaskRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromRequest(UpdateTaskRequest request, @MappingTarget Task task);
}