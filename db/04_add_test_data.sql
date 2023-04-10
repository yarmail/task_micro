/*
 При желании можно добавить тестовые данные,
 чтобы удобнее было тестировать
 Примечание: присутствуют вспомогательные запросы
 */

/*
Добавляем приоритеты
title varchar NOT NULL,
color varchar NOT NULL
 */
INSERT INTO priority (title, color)  VALUES
('Низкий', '#caffdd'), ('Средний', '#fdffcc'), ('Высокий', '#ffcccc');

/*
Добавляем категории
title varchar NOT NULL,
 */
UPDATE category SET completed_count = 0;
UPDATE category SET uncompleted_count = 0;
INSERT INTO category(title) VALUES
('Семья'), ('Работа'), ('Отдых');

/*
 Добавляем 1 строку для статистики
 */
INSERT INTO stat (id) VALUES (1);

/*
 Добавляем задачи в таблицу task
 После внесения данных можно
 посмотреть как сработал триггер в таблицах category и stat
 */
TRUNCATE task RESTART IDENTITY;
INSERT INTO task (title, completed, task_date, priority_id, category_id) VALUES
('Задача 1', 0, '2023-03-07 10:00:00', 1, 1),
('Задача 2', 1, '2023-03-07 11:00:00', 2, 2),
('Задача 3', 0, '2023-03-07 12:00:00', 3, 3);