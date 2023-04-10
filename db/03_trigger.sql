/*
Добавляем функции и триггеры для статистики
*/

/*
При добавлении (INSERT) новой задачи в таблицу task
ведем статистику в таблице category
по соответствующим колонкам и общую статистику в stat
*/
CREATE OR REPLACE FUNCTION func_task_insert()
RETURNS trigger AS $$
BEGIN
    -- категория не пустая, статус задачи завершен = 1
    IF COALESCE(NEW.category_id, 0) > 0 AND COALESCE(NEW.completed, 0) = 1 THEN
        UPDATE category SET completed_count = COALESCE(completed_count, 0) + 1
        WHERE id = NEW.category_id;
    END IF;
    -- категория не пустая, статус задачи НЕ завершен = 0
    IF COALESCE(NEW.category_id, 0) > 0 AND COALESCE(NEW.completed, 0) = 0 THEN
        UPDATE category SET uncompleted_count = COALESCE(uncompleted_count, 0) + 1
        WHERE id = NEW.category_id;
    END IF;
    -- для stat - общая статистика
    IF COALESCE(NEW.completed, 0) = 1 THEN
        UPDATE stat SET completed_total = COALESCE(completed_total, 0) + 1
        WHERE id = 1;
    ELSE
        UPDATE stat SET uncompleted_total = COALESCE(uncompleted_total, 0) + 1
        WHERE id = 1;
    END IF;
    RETURN NULL; -- возвращаем, если ни один из if не сработал
END;
$$ LANGUAGE plpgsql;

/*
 Теперь создадим триггер на операцию AFTER INSERT
 c привязкой к функции и к таблице task
 который будет срабатывать при добавлении каждой строки
*/

DROP TRIGGER IF EXISTS trig_insert ON task;
CREATE TRIGGER trig_insert AFTER INSERT ON task
FOR EACH ROW EXECUTE PROCEDURE func_task_insert();

/*
 ------------------
 Функции и триггеры для операции UPDATE
 */
CREATE OR REPLACE FUNCTION func_task_update()
    RETURNS trigger AS $$
BEGIN
    -- изменили completed на 1, не меняли категорию
    IF COALESCE(OLD.completed, 0) <> COALESCE(NEW.completed, 0)
        AND NEW.completed = 1
        AND COALESCE(OLD.category_id, 0) = COALESCE(NEW.category_id, 0) THEN
        -- у категории количество незавершенных уменьшится на 1
        -- количество завершенных увеличится на 1
        UPDATE category SET uncompleted_count = COALESCE(uncompleted_count, 0) - 1,
                            completed_count = COALESCE(completed_count, 0) + 1
        WHERE id = OLD.category_id;

        -- общая статистика
        UPDATE stat SET uncompleted_total = COALESCE(uncompleted_total, 0) - 1,
                         completed_total = COALESCE(completed_total, 0) + 1
        WHERE id = 1;
    END IF;

    -- изменили completed на 0, НЕ изменили категорию
    IF  COALESCE(OLD.completed, 0) <> COALESCE(NEW.completed, 0)
        AND NEW.completed = 0
        AND COALESCE(OLD.category_id, 0) = COALESCE(NEW.category_id, 0) THEN
        -- у категории количество завершенных уменьшится на 1
        -- количество незавершенных увеличится на 1
        UPDATE category SET completed_count = COALESCE(completed_count, 0) - 1,
                            uncompleted_count = COALESCE(uncompleted_count, 0) + 1
        WHERE id = OLD.category_id;
        -- общая статистика
        UPDATE stat SET completed_total = COALESCE(completed_total, 0) - 1,
                        uncompleted_total = COALESCE(uncompleted_total, 0) + 1
        WHERE id = 1;
    END IF;

    -- изменили категорию для неизменного completed = 1
    IF COALESCE(OLD.completed, 0) = COALESCE(NEW.completed, 0)
        AND NEW.completed = 1
        AND COALESCE(OLD.category_id, 0) <> COALESCE(NEW.category_id, 0) THEN
        -- у старой категории количество завершенных уменьшится на 1
        UPDATE category SET completed_count = COALESCE(completed_count, 0) - 1
        WHERE id = OLD.category_id;
        -- у новой категории кол-во завершенных увеличится на 1
        UPDATE category SET completed_count = COALESCE(completed_count, 0) + 1
        WHERE id = NEW.category_id;
        -- общая статистика не меняется
    END IF;

    -- изменили категорию для неизменного completed = 0
    IF COALESCE(OLD.completed, 0) = COALESCE(NEW.completed, 0)
        AND NEW.completed = 0
        AND COALESCE(OLD.category_id, 0) <> COALESCE(NEW.category_id, 0) THEN
        -- у старой категории количество незавершенных уменьшится на 1
        UPDATE category SET uncompleted_count = COALESCE(uncompleted_count, 0) - 1
        WHERE id = OLD.category_id;
        -- у новой категории кол-во незавершенных увеличится на 1
        UPDATE category SET uncompleted_count = COALESCE(uncompleted_count, 0) + 1
        WHERE id = NEW.category_id;
        -- общая статистика не меняется
    END IF;

    -- изменили категорию, изменили completed c 1 на 0
    IF COALESCE(OLD.completed, 0) <> COALESCE(NEW.completed, 0)
        AND NEW.completed = 0
        AND COALESCE(OLD.category_id, 0) <> COALESCE(NEW.category_id, 0) THEN
        -- у старой категории кол-во завершенных задач уменьшится на 1
        UPDATE category SET completed_count = COALESCE(completed_count, 0) - 1
        WHERE id = OLD.category_id;
        -- у новой категории кол-во незавершенных увеличится на 1
        UPDATE category SET uncompleted_count = COALESCE(uncompleted_count, 0) + 1
        WHERE id = NEW.category_id;
        -- общая статистика
        UPDATE stat SET uncompleted_total = COALESCE(uncompleted_total, 0) + 1,
                        completed_total = COALESCE(completed_total, 0) - 1
        WHERE id = 1;
    END IF;

    -- изменили категорию, изменили completed c 0 на 1
    IF COALESCE(OLD.completed, 0) <> COALESCE(NEW.completed, 0)
        AND NEW.completed = 1
        AND COALESCE(OLD.category_id, 0) <> COALESCE(NEW.category_id, 0) THEN
        -- у старой категории количество незавершенных уменьшится на 1
        UPDATE category SET uncompleted_count = COALESCE(uncompleted_count, 0) - 1
        WHERE id = OLD.category_id;
        -- у новой категории количество завершенных увеличится на 1
        update category SET completed_count = COALESCE(completed_count, 0) + 1
        WHERE id = NEW.category_id;
        -- общая статистика
        UPDATE stat SET uncompleted_total = COALESCE(uncompleted_total, 0) - 1,
                        completed_total = COALESCE(completed_total, 0) + 1
        WHERE id = 1;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

/*
Теперь к этой большой функции func_task_update()
добавляем триггер trig_update
 */
DROP TRIGGER IF EXISTS trig_update ON task;
CREATE TRIGGER trig_update AFTER UPDATE ON task
FOR EACH ROW EXECUTE PROCEDURE func_task_update();

/*
 ---------------------
 Функции и триггеры для операции DELETE
 */
CREATE OR REPLACE FUNCTION func_task_delete()
RETURNS trigger AS $$
BEGIN
    -- категория непустая и статус задачи завершен = 1
    IF COALESCE(OLD.category_id, 0) > 0 AND COALESCE(OLD.completed, 0) = 1 THEN
        UPDATE category SET completed_count = COALESCE(completed_count, 0) - 1
        WHERE id = OLD.category_id;
    END IF;
    -- категория непустая и статус задачи незавершен = 0
    IF COALESCE(OLD.category_id, 0) > 0 AND COALESCE(OLD.completed, 0) = 0 THEN
        UPDATE category SET uncompleted_count = COALESCE(uncompleted_count, 0) - 1
        WHERE id = OLD.category_id;
    END IF;
    -- общая статистика
    IF COALESCE(OLD.completed, 0) = 1 THEN
        UPDATE stat SET completed_total = COALESCE(completed_total, 0) - 1
        WHERE id = 1;
    ELSE
        UPDATE stat SET uncompleted_total = COALESCE(uncompleted_total, 0) - 1
        WHERE id = 1;
    END IF;
    RETURN NULL;
END;
$$
LANGUAGE plpgsql;
/*
Теперь к функции func_task_delete()
добавляем триггер trig_delete
 */
DROP TRIGGER IF EXISTS trig_delete ON task;
CREATE TRIGGER trig_delete AFTER DELETE ON task
FOR EACH ROW EXECUTE PROCEDURE func_task_delete();