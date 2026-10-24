package com.dev.taskmanager.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dev.taskmanager.model.Task;

@Repository
public class TaskRepository {

    private static final RowMapper<Task> TASK_ROW_MAPPER = (resultSet, i) -> {
        Task task = new Task();
        task.setId(resultSet.getLong("id"));
        task.setTitle(resultSet.getString("title"));
        task.setDescription(resultSet.getString("description"));
        task.setCompleted(resultSet.getBoolean("completed"));
        task.setCreatedAt(resultSet.getObject("created_at", LocalDateTime.class));
        return task;
    };

    private final JdbcTemplate jdbc;

    public TaskRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Task save(Task task) {
        String sql = "INSERT INTO tasks (title, description, completed) VALUES (?, ?, ?) RETURNING id, title, description, completed, created_at";
        return jdbc.queryForObject(sql, TASK_ROW_MAPPER, task.getTitle(), task.getDescription(), task.isCompleted());
    }

    public Optional<Task> findById(Long id) {
        String sql = "SELECT id, title, description, completed, created_at FROM tasks WHERE id = ?";
        try {
            Task task = jdbc.queryForObject(sql, TASK_ROW_MAPPER, id);
            return Optional.of(task);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Task> findAll() {
        String sql = "SELECT id, title, description, completed, created_at FROM tasks ORDER BY created_at, id";
        return jdbc.query(sql, TASK_ROW_MAPPER);
    }

    public List<Task> findByCompleted(boolean completed) {
        String sql = "SELECT id, title, description, completed, created_at FROM tasks WHERE completed = ? ORDER BY created_at, id";
        return jdbc.query(sql, TASK_ROW_MAPPER, completed);
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        int rows = jdbc.update(sql, id);
        return rows == 1;
    }

    public Optional<Task> markCompleted(Long id) {
        String sql = "UPDATE tasks SET completed = true WHERE id = ? RETURNING id, title, description, completed, created_at";
        try {
            Task task = jdbc.queryForObject(sql, TASK_ROW_MAPPER, id);
            return Optional.of(task);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
