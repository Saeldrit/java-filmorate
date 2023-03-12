package ru.yandex.practicum.filmorate.repository.abstraction;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.repository.projection.InteractionWithFilm;
import ru.yandex.practicum.filmorate.repository.projection.InteractionWithFilmLibrary;

@Repository
public abstract class AbstractFabricFilmRepository<I, T> extends AbstractCRUDRepository<I, T>
		implements InteractionWithFilmLibrary<I, T>, InteractionWithFilm {
}
