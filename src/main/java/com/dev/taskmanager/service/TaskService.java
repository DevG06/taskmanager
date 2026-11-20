package com.dev.taskmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.taskmanager.dto.CreateTaskRequest;
import com.dev.taskmanager.dto.TaskResponse;
import com.dev.taskmanager.dto.UpdateTaskRequest;
import com.dev.taskmanager.exception.TaskNotFoundException;
import com.dev.taskmanager.mapper.TaskMapper;
import com.dev.taskmanager.model.Task;
import com.dev.taskmanager.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public List<TaskResponse> getAllTasks(Boolean completed) {
        List<Task> tasks;
        if(completed == null) {
            tasks = taskRepository.findAllByOrderByCreatedAtAscIdAsc();
        } else {
            tasks = taskRepository.findByCompletedOrderByCreatedAtAscIdAsc(completed);
        }
        return tasks.stream().map(taskMapper::toResponse).toList();
    }

    public TaskResponse getTask(Long id) {
        return taskRepository.findById(id).map(taskMapper::toResponse).orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Transactional
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.delete(task);
    }

    public TaskResponse createTask(CreateTaskRequest request) {
        if (request.title() == null || request.title().isBlank()) {
            throw new IllegalArgumentException("Title is mandatory");
        }
        Task task = taskMapper.toEntity(request);
        task.setTitle(task.getTitle().trim());
        return taskMapper.toResponse(taskRepository.save(task));
    }

    @Transactional 
    public TaskResponse updateTask(Long id, UpdateTaskRequest request) {
        if (request.title() != null && request.title().isBlank()) {
            throw new IllegalArgumentException("Title can't be blank");
        }
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        taskMapper.updateEntityFromRequest(request, task);
        task.setTitle(task.getTitle().trim());
        return taskMapper.toResponse(task);
    }
}
