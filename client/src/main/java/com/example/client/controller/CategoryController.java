package com.example.client.controller;

import com.example.client.entity.Category;
import com.example.client.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping("/save") //add
    public ResponseEntity<Category> save(@RequestBody Category category) {
        if (category.getId() != null && category.getId() != 0) {
            return new ResponseEntity("bad param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("bad param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(service.save(category));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Category category) {
        if (category.getId() != null && category.getId() != 0) {
            return new ResponseEntity("bad param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("bad param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        service.update(category);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        if(!service.deleteById(id)) {
            return new ResponseEntity("bad param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<Category> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable int id) {
        Optional<Category> optCategory = service.findById(id);
        if (optCategory.isEmpty()) {
            return new ResponseEntity("id not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(optCategory.get());
    }
}