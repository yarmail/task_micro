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
@Table(name = "priority")
public class Priority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "color")
    private String color;
}

/*
Примечания

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EqualsAndHashCode.Include
Эквалс и хешкод делаем по id, т.к. этого достаточно
и быстро

 */