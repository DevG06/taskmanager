package com.dev.taskmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dev.taskmanager.dto.CreateTaskRequest;
import com.dev.taskmanager.exception.TaskNotFoundException;
import com.dev.taskmanager.model.Task;
import com.dev.taskmanager.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task completeTask(Long id) {
        return taskRepository.markCompleted(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    public void deleteTask(Long id) {
        if (!taskRepository.deleteById(id)) {
            throw new TaskNotFoundException(id);
        }
    }

    public Task createTask(CreateTaskRequest request) {
        if (request.title() == null || request.title().isBlank()) {
            throw new IllegalArgumentException("Title is mandatory");
        }
        Task task = new Task();
        task.setTitle(request.title().trim());
        task.setDescription(request.description());
        task.setCompleted(request.completed());
        return taskRepository.save(task);
    }
}
