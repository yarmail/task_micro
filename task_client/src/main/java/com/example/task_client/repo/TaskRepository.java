package com.example.task_client.repo;

import com.example.entity.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TaskRepository extends JpaRepository<Task, Integer> {

    boolean deleteById(int id);

    /**
     * Примечания.
     * Расширенный поиск по всем параметрам, касаемым задачи.
     * Метод получает данные, например String title
     * и в виде "title" подставляет их в запрос в виде :title
     *
     * Если в запросе параметр отсутствует, то возвращается весь
     * список задач
     * :title is null or :title=''
     *
     * Поиск осушествляется в любом месте строки
     * concat('%', :title,'%')
     *
     */
    @Query("SELECT p FROM Task p where " +
            "(:title is null or :title='' or lower(p.title) like lower(concat('%', :title,'%'))) and" +
            "(:completed is null or p.completed=:completed) and " +
            "(:priorityId is null or p.priority.id=:priorityId) and " +
            "(:categoryId is null or p.category.id=:categoryId)"
    )
    Page<Task> findByParams(@Param("title") String title,
                            @Param("completed") Integer completed,
                            @Param("priorityId") Integer priorityId,
                            @Param("categoryId") Integer categoryId,
                            Pageable pageable
    );
}