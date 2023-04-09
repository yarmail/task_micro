package com.example.task_client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String test() {
        return "проект запущен";
    }
}

/* Примечения
Это простой тестовый контроллер для проверки
первоначально работоспособности проекта.
Если нажать ссылку в консоле при запуске проекта,
http://localhost:8080/
контроллер должен перехватить этот запрос
и выдать ответ в браузере - проект запущен
 */
