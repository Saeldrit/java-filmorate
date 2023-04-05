package ru.yandex.practicum.filmorate.repository.memory_repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.abstraction.AbstractFabricFilmRepository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class FilmRepository extends AbstractFabricFilmRepository<Integer, Film> {

	private Integer intermediateId;

	public FilmRepository() {
		this.intermediateId = 0;
	}

	protected Integer generateId() {
		return ++intermediateId;
	}

	@Override
	public Film save(Film film) {
		if (searchCopy(film)) {
			Film filmWithId = ifThereIsNoIdGenerateIt(film);
			getObjectMap().putIfAbsent(filmWithId.getId(), filmWithId);
		}

		return getObjectMap().get(intermediateId);
	}

	@Override
	public Film update(Film film) {
		if (getObjectMap().containsKey(film.getId())) {
			getObjectMap().put(film.getId(), film);

			return getObjectMap().get(film.getId());
		}
		return null;
	}

	@Override
	public Film like(Integer filmId, Integer userId) {
		Film film = getObjectMap().get(filmId);
		film.addToLikesSet(userId);
		return update(film);
	}

	@Override
	public Film removeLike(Integer filmId, Integer userId) {
		Film film = getObjectMap().get(filmId);
		film.removeFromLikesSet(userId);
		return update(film);
	}

	@Override
	public List<Film> returnPopularFilms(Integer count) {
		Map<Integer, Film> sortedMap = bringBackIdTopMovies(count);

		return new ArrayList<>(sortedMap.values());
	}

	private Film ifThereIsNoIdGenerateIt(Film film) {
		return film.getId() != null ?
				film :
				film.withId(generateId());
	}

	private Map<Integer, Film> bringBackIdTopMovies(Integer limit) {
		return getObjectMap()
				.entrySet()
				.stream()
				.sorted(Collections.reverseOrder(
						Map.Entry.comparingByValue(
								Comparator.comparing(Film::countLikes))))
				.limit(limit)
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						Map.Entry::getValue,
						(a, b) -> a,
						LinkedHashMap::new
				));
	}
}
