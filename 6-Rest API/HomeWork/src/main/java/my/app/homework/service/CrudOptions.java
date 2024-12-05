package my.app.homework.service;

import java.util.List;

/**
 * Предоставляем список обязательных CRUD операций.
 * @param <T>
 */
public interface CrudOptions<T> {
    /**
     * Создать новый объект
     * @param entity
     * @return
     */
    T create(T entity);

    /**
     * Получить объект по ID
     * @param id
     * @return
     */
    T readById(Long id);

    /**
     * Получить все объекты
     * @return
     */
    List<T> readAll();

    /**
     * Обновить объект
     * @param entity
     * @return
     */
    T update(T entity);

    /**
     * Удалить объект по ID
     * @param id
     * @return
     */
    T deleteById(Long id);
}
