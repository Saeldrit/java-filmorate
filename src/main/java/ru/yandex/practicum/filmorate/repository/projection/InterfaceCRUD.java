package ru.yandex.practicum.filmorate.repository.projection;

import java.util.Collection;

public interface InterfaceCRUD<I, T> {
    Collection<T> bringBackAllValues();

    T save(T entity);

    T removeById(I id);

    T findById(I id);

    T update(T entity);

    void cleaner();
}
