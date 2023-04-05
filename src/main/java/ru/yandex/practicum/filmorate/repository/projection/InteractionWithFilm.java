package ru.yandex.practicum.filmorate.repository.projection;

import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.ArrayList;
import java.util.List;

public interface InteractionWithFilm {
	default MPA MPAById(Integer id) {
		return null;
	}

	default List<MPA> listMPA() {
		return new ArrayList<>();
	}

	default Genre genreById(Integer id) {
		return null;
	}

	default List<Genre> listGenres() {
		return new ArrayList<>();
	}
}
