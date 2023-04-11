package com.example.client.repo;

import com.example.client.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean deleteById(int id);

    /**
     * Пример расширенного запроса
     * В данном случае мы реализуем поиск в катогориях (в названиях)
     *
     * Искомая строка может быть в любом месте, в начале, середине или
     * в конце строки.
     * concat('%', :title,'%'))
     *
     * В случае, если искомая строка не найдена, выдаются все значения
     * :title is null or :title=''
     *
     * Вместо :title передаются значания аргументов String title
     */
    @Query("SELECT c FROM Category c where " +
            "(:title is null or :title='' or lower(c.title) like lower(concat('%', :title,'%')))  " +
            "order by c.title asc")
    List<Category> findByTitle(@Param("title") String title);
}

/* Примечания

boolean deleteById(int id);
Видоизменяем операцию удаления по id, чтобы она что-то возвращала

List<Category> findByTitle(@Param("title") String title);
Помимо стандартных возможностей CRUD предоставляемых интерфейсом
можно добавить запросы с помощью
JPQL (Java Persistence Query Language), который похож на SQL,
но с некоторыми особенностями.
Про параметры.
Когда поступает String title, @Param передает его в параметр "title",
и этот параметр будет использоваться в SQL запросе.
SQL запрос.
Для создания SQL запроса используется @Query. Этот SQL
запрос будет вызываться, когда будет вызываться метод.

 */