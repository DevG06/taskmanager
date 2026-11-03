package com.dev.taskmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.taskmanager.dto.CreateTaskRequest;
import com.dev.taskmanager.dto.TaskResponse;
import com.dev.taskmanager.exception.TaskNotFoundException;
import com.dev.taskmanager.model.Task;
import com.dev.taskmanager.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted(), task.getCreatedAt());
    }

    public List<TaskResponse> getAllTasks(Boolean completed) {
        List<Task> tasks;
        if(completed == null) {
            tasks = taskRepository.findAllByOrderByCreatedAtAscIdAsc();
        } else {
            tasks = taskRepository.findByCompletedOrderByCreatedAtAscIdAsc(completed);
        }
        return tasks.stream().map(this::toTaskResponse).toList();
    }

    public TaskResponse getTask(Long id) {
        return taskRepository.findById(id).map(this::toTaskResponse).orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Transactional 
    public TaskResponse completeTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        task.setCompleted(true);
        return toTaskResponse(task);
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
        Task task = new Task();
        task.setTitle(request.title().trim());
        task.setDescription(request.description());
        task.setCompleted(request.completed());
        return toTaskResponse(taskRepository.save(task));
    }
}
