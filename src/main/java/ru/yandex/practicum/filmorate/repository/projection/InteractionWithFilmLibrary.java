package ru.yandex.practicum.filmorate.repository.projection;

import java.util.ArrayList;
import java.util.List;

public interface InteractionWithFilmLibrary<I, T> extends InterfaceCRUD<I, T> {
	default T like(I filmId, I userId) {
		return null;
	}

	default T removeLike(I filmId, I userId) {
		return null;
	}

	default List<T> returnPopularFilms(I count) {
		return new ArrayList<>();
	}
}
