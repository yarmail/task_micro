package com.example.client.service;

import com.example.client.entity.Category;
import com.example.client.repo.CategoryRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Category save(Category category) {
        return repository.save(category);
    }

    public void update(Category category) {
        repository.save(category);
    }

    public boolean deleteById(int id) {
        return repository.deleteById(id);
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Optional<Category> findById(int id) {
        return repository.findById(id);
    }

    /**
     * Поиск по категориям
     */
    public List<Category> search(String text) {
        return repository.findByTitle(text);
    }
}