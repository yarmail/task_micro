package com.example.client.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "completed")
    private Integer completed;

    @Column(name = "task_date")
    private Timestamp taskDate;

    @ManyToOne
    @JoinColumn(name = "priority_id", referencedColumnName = "id")
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
}
/*
Примечания

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EqualsAndHashCode.Include
Эквалс и хешкод делаем по id, т.к. этого достаточно
и быстро

Из генерации удалил поля priorityId, categoryId,
нужны будут объекты а не id.

@ManyToOne
Много задач имеют один приоритет и одну категорию

@JoinColumn(name = "priority_id", referencedColumnName = "id")
Благодаря этой связке при добавлении новых составных объектов
Hibernate возьмет поле id у Priority и в таблице Task
сохранит сохранит его в поле priority_id.
 */