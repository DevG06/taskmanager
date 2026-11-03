package com.dev.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.taskmanager.model.Task;


public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCompletedOrderByCreatedAtAscIdAsc(boolean completed);
    List<Task> findAllByOrderByCreatedAtAscIdAsc();
}
