package com.example.client.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Объект для работы с поиском по объектам Task,
 * Возможные значения, по которым можно искать задачи +
 * поля для сортировки и постраничности
 * Примечания:
 * 1. Поля для поиска должны быть объектами,
 * чтобы можно было передать null
 * 2. Поля pageNumber, pageSize используем для постраничности
 * 3. Поля sortColumn (столбец сортировки),
 * sortDirection (направление сортировки) -
 * используем для сортировки
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskSearchValues {

    private String title;
    private Integer completed;
    private Integer priorityId;
    private Integer categoryId;

    private Integer pageNumber;
    private Integer pageSize;

    private String sortColumn;
    private String sortDirection;
}
