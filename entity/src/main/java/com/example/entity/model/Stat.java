package com.example.entity.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "stat")
public class Stat {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Integer id;

    @Column(name = "completed_total")
    private Integer completedTotal;

    @Column(name = "uncompleted_total")
    private Integer uncompletedTotal;
}

/*
 Примечания

 @Id - в таблице нет первичного ключа, там одна строка
 Эта аннотация - дань Хибернейту

 Пока непонятно, зачем здесь прописывать
 поля  completedTotal, uncompletedTotal,
 по проекту они заполняются триггерами БД

 */
