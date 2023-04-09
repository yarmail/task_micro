package com.example.task_client.controller;

import com.example.entity.model.Priority;
import com.example.task_client.service.PriorityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/priority")
public class PriorityController {

    private final PriorityService service;

    public PriorityController(PriorityService service) {
        this.service = service;
    }

    @PostMapping("/save") //add
    public ResponseEntity<Priority> save(@RequestBody Priority priority) {
        if (priority.getId() != null && priority.getId() != 0) {
            return new ResponseEntity("bad param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("bad param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(service.save(priority));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Priority priority) {
        if (priority.getId() != null && priority.getId() != 0) {
            return new ResponseEntity("bad param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("bad param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        service.update(priority);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        if(!service.deleteById(id)) {
            return new ResponseEntity("bad param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * для примера работы JPA Named Queries
     * изменим в репозотории вывод всех, сделаем его
     * сортированным в алфавитном порядке по полю title
     */
    @GetMapping("/all")
    public List<Priority> findAll() {
        return service.findAllByOrderByTitleAsc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Priority> findById(@PathVariable int id) {
        Optional<Priority> optPriority = service.findById(id);
        if (optPriority.isEmpty()) {
            return new ResponseEntity("id not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(optPriority.get());
    }
}