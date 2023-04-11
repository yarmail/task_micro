package com.example.client.service;

import com.example.client.entity.Priority;
import com.example.client.repo.PriorityRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PriorityService {

    private final PriorityRepository repository;

    public PriorityService(PriorityRepository repository) {
        this.repository = repository;
    }

    public Priority save(Priority priority) {
        return repository.save(priority);
    }

    public void update(Priority priority) {
        repository.save(priority);
    }

    public boolean deleteById(int id) {
        return repository.deleteById(id);
    }

    public List<Priority> findAllByOrderByTitleAsc() {
        return repository.findAllByOrderByTitleAsc();
    }

    public Optional<Priority> findById(int id) {
        return repository.findById(id);
    }
}
