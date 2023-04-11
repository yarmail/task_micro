package com.example.client.controller;

import com.example.client.entity.Task;
import com.example.client.search.TaskSearchValues;
import com.example.client.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {

    public static final String ID_COLUMN = "id";
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping("/save") //add
    public ResponseEntity<Task> save(@RequestBody Task task) {
        if (task.getId() != null && task.getId() != 0) {
            return new ResponseEntity("bad param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity("bad param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(service.save(task));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Task task) {
        if (task.getId() != null && task.getId() != 0) {
            return new ResponseEntity("bad param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity("bad param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        service.update(task);
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
    public List<Task> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable int id) {
        Optional<Task> optTask = service.findById(id);
        if (optTask.isEmpty()) {
            return new ResponseEntity("id not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(optTask.get());
    }

    /**
     * Поиск по любым параметрам заданий
     * TaskSearchValues содержит все возможные параметры поиска
     * (возможно всю оброботку следовало бы перенести в Сервис)
     *
     */
    @PostMapping("/search")
    public ResponseEntity<Page<Task>> search(@RequestBody TaskSearchValues taskSearchValues) {

        // исключить NullPointerException
        String text = taskSearchValues.getTitle() != null
                ? taskSearchValues.getTitle() : null;

        // конвертируем Boolean в Integer
        Integer completed = taskSearchValues.getCompleted() != null
                ? taskSearchValues.getCompleted() : null;

        Integer priorityId = taskSearchValues.getPriorityId() != null
                ? taskSearchValues.getPriorityId() : null;
        Integer categoryId = taskSearchValues.getCategoryId() != null
                ? taskSearchValues.getCategoryId() : null;

        String sortColumn = taskSearchValues.getSortColumn() != null
                ? taskSearchValues.getSortColumn() : "title";
        String sortDirection = taskSearchValues.getSortDirection() != null
                ? taskSearchValues.getSortDirection() : null;

        //ввел временно значения по умолчанию, т.к. без них падала ошибка
        // возможно с появлением front значения можно будет менять по-другому
        Integer pageNumber = taskSearchValues.getPageNumber() != null
                ? taskSearchValues.getPageNumber() : 0;
        Integer pageSize = taskSearchValues.getPageSize() != null
                ? taskSearchValues.getPageSize() : 10;

        // направление сортировки
        Sort.Direction direction = sortDirection == null
                || sortDirection.trim().length() == 0
                || sortDirection.trim().equals("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC;


        // подставляем все значения


        /**
         * Объект сортировки, который содержит стобец и направление
         * Вторым полем для сортировки добавляем id, чтобы всегда сохранялся строгий порядок.
         * Например, если у 2-х задач одинаковое значение приоритета и мы сортируем по этому полю.
         * Порядок следования этих 2-х записей после выполнения запроса может каждый раз меняться,
         * т.к. не указано второе поле сортировки.
         * Поэтому и используем ID - тогда все записи с одинаковым значением приоритета
         * будут следовать в одном порядке по ID.
         */
        Sort sort = Sort.by(direction, sortColumn, ID_COLUMN);

        // объект постраничности
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        // результат запроса с постраничным выводом
        Page result = service.findByParams(text, completed, priorityId,
                categoryId, pageRequest);

        // результат запроса
        return ResponseEntity.ok(result);
    }
}