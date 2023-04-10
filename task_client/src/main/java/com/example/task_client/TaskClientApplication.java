package com.example.task_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"com.example.task_micro"})
@EnableJpaRepositories(basePackages = {"com.example.task_micro.task_client"})
public class TaskClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskClientApplication.class, args);
    }
}

/* Примечание
Прежде всего добавляем аннотации в файл
task_micro\task_client\src\main\java\com\example\task_client\TaskClientApplication.java
@ComponentScan(basePackages = {"com.example.task_micro"})
@EnableJpaRepositories(basePackages = {"com.example.task_micro.task_client"})
иначе Спринг не может создать бины и возникает такая ошибка
"org.springframework.beans.factory.UnsatisfiedDependencyException:
Error creating bean with name ..."
 */