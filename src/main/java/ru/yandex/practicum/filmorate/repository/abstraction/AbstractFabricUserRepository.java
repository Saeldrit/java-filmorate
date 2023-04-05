package ru.yandex.practicum.filmorate.repository.abstraction;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.repository.projection.InteractionWithFriend;
import ru.yandex.practicum.filmorate.repository.projection.InteractionWithFilmLibrary;

@Repository
public abstract class AbstractFabricUserRepository<I, T> extends AbstractCRUDRepository<I, T>
		implements InteractionWithFriend<I, T>,
		InteractionWithFilmLibrary<I, T> {
}
