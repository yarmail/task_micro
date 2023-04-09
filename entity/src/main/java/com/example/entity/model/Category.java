package com.example.entity.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "completed_count")
    private Integer completedCount;

    @Column(name = "uncompleted_count")
    private Integer uncompletedCount;
}

/*
Примечания

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EqualsAndHashCode.Include
Эквалс и хешкод делаем по id, т.к. этого достаточно
и быстро
 */