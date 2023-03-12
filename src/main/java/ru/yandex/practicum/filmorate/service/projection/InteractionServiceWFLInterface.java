package ru.yandex.practicum.filmorate.service.projection;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

public interface InteractionServiceWFLInterface {

	Film putLike(Integer filmId, Integer userId);

	Film removeLike(Integer filmId, Integer userId);

	List<Film> popularFilms(Integer count);

	MPA MPAbyId(Integer id);

	List<MPA> MPAList();

	Genre genreById(Integer id);

	List<Genre> genresList();
}
