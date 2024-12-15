package my.app.homework.service;

import java.util.List;

public interface CrudOptions<T> {
    T save(T entity);
    T getById(Long id);
    List<T> getAll();
    T update(T entity);
    T deleteById(Long id);
}
