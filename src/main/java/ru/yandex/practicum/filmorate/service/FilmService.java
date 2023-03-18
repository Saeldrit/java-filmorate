package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.abstraction.AbstractFabricFilmRepository;
import ru.yandex.practicum.filmorate.service.projection.FilmServiceInterface;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService implements FilmServiceInterface {

	private final AbstractFabricFilmRepository<Integer, Film> filmRepositoryDB;

	@Override
	public List<Film> returnAllFilms() {
		return new ArrayList<>(filmRepositoryDB.bringBackAllValues());
	}

	@Override
	public Film addNewFilm(Film film) {
		log.info("ATTEMPT TO CREATE '{}' id = {}"
				, film.getName()
				, film.getId());
		return filmRepositoryDB.save(film);
	}

	@Override
	public Film findFilmById(Integer id) {
		throwNewEntityNotFoundException(id);
		log.info("FIND FILM BY ID '{}'", id);
		return filmRepositoryDB.findById(id);
	}

	@Override
	public Film updateFilm(Film film) {
		throwNewEntityNotFoundException(film.getId());
		log.info("UPDATE FILM HAS ARRIVED '{}'", film.getId());
		return filmRepositoryDB.update(film);
	}

	@Override
	public Film removeFilm(Integer id) {
		throwNewEntityNotFoundException(id);
		log.info("REMOVE FILM HAS ARRIVED BY ID '{}'", id);
		return filmRepositoryDB.removeById(id);
	}

	@Override
	public void cleaner() {
		log.warn("REPOSITORY CLEARED");
		filmRepositoryDB.cleaner();
	}

	private void throwNewEntityNotFoundException(Integer id) {
		if (id == null) {
			throw new EntityNotFoundException("Film id is Null");
		}
		if (filmRepositoryDB.findById(id) == null) {
			throw new EntityNotFoundException("Film not found by id " + id);
		}
	}
}
