package ru.yandex.practicum.filmorate.service.projection;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Service
public interface FilmServiceInterface {
    List<Film> returnAllFilms();

    Film addNewFilm(Film film);

    Film findFilmById(Integer id);

    Film updateFilm(Film film);

    Film removeFilm(Integer id);

    void cleaner();
}
