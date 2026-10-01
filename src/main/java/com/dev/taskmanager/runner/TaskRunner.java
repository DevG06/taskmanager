package com.dev.taskmanager.runner;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.dev.taskmanager.dto.CreateTaskRequest;
import com.dev.taskmanager.exception.TaskNotFoundException;
import com.dev.taskmanager.model.Task;
import com.dev.taskmanager.service.TaskService;

@Component
public class TaskRunner implements CommandLineRunner {

    private final TaskService taskService;

    public TaskRunner(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void run(String... args) throws Exception {
        CreateTaskRequest request = new CreateTaskRequest(" Write Title ", "Create with spaced out tile", false);
        Task task = runCase("1. Create", () -> taskService.createTask(request));
        runCase("2. Get Existing", () -> taskService.getTask(task.getId()));
        runCase("3. Get Missing", () -> taskService.getTask(-1L));
        List<Task> tasks = runCase("4. List", () -> taskService.getAllTasks());
        System.out.println("Count : " + tasks.size());
        runCase("5. Complete", () -> taskService.completeTask(task.getId()));
        runCase("6. Delete Existing", () -> {
            taskService.deleteTask(task.getId());
            return "Deleted";
        });
        runCase("7. Delete Missing", () -> {
            taskService.deleteTask(task.getId());
            return "Deleted";
        });
        CreateTaskRequest requestBlank = new CreateTaskRequest("  ", "Blank Title", false);
        runCase("8. Blank Title", () -> taskService.createTask(requestBlank));
    }

    private <T> T runCase(String label, Supplier<T> action) {
        System.out.println("--- " + label + " ---");
        try {
            T result = action.get();
            System.out.println(result);
            return result;
        } catch (TaskNotFoundException | IllegalArgumentException e) {
            System.out.println(e.getClass().getSimpleName() + " : " + e.getMessage());
            return null;
        }
    }
}
