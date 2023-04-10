/*
 Перед началом работы или в ходе очередного
 эксперимента может пригодится удаление
 всей структуры таблиц, что бы вновь сделать все
 заново
 Я предполагаю, что если вы специально не указываете
 схему (папку) для ваших таблиц, она будет находится
 в схеме (папке) public.
 Отсюда один из самых простых способов все удалить -
 это просто удалить папку public и создать её заново.
 Пользуйтесь с осторожностью

 Source (источник)
 https://stackoverflow.com/questions/3327312/how-can-i-drop-all-the-tables-in-a-postgresql-database
 */
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;
COMMENT ON SCHEMA public IS 'standard public schema';

/*
 Также время от времени может пригодится очистка какой-либо из таблиц
 со сбросом id к нулю
 В этом может помочь следующий шаблон:

 truncate table tbl restart identity;
 */
