package com.example.task_client.service;

import com.example.entity.model.Task;
import com.example.task_client.repo.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task save(Task task) {
        return repository.save(task);
    }

    public void update(Task task) {
        repository.save(task);
    }

    public boolean deleteById(int id) {
        return repository.deleteById(id);
    }

    public List<Task> findAll() {
        return repository.findAll();
    }

    public Optional<Task> findById(int id) {
        return repository.findById(id);
    }

    /**
     * Специальный метод для поиска задач по всем параметрам
     */
    public Page findByParams(String text, Integer completed,
                             Integer priorityId, Integer categoryId,
                             PageRequest pageable) {
        return repository.findByParams(text, completed, priorityId,
                categoryId, pageable);
    }
}