package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.abstraction.AbstractFabricFilmRepository;
import ru.yandex.practicum.filmorate.service.projection.FilmServiceInterface;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FilmService implements FilmServiceInterface {

	private final AbstractFabricFilmRepository<Integer, Film> repository;

	@Autowired
	public FilmService(@Qualifier("filmRepositoryDB")
					   AbstractFabricFilmRepository<Integer, Film> repository) {
		this.repository = repository;
	}

	@Override
	public List<Film> returnAllFilms() {
		return new ArrayList<>(repository.bringBackAllValues());
	}

	@Override
	public Film addNewFilm(Film film) {
		log.info("ATTEMPT TO CREATE '{}' id = {}"
				, film.getName()
				, film.getId());
		return repository.save(film);
	}

	@Override
	public Film findFilmById(Integer id) {
		throwNewEntityNotFoundException(id);
		log.info("FIND FILM BY ID '{}'", id);
		return repository.findById(id);
	}

	@Override
	public Film updateFilm(Film film) {
		throwNewEntityNotFoundException(film.getId());
		log.info("UPDATE FILM HAS ARRIVED '{}'", film.getId());
		return repository.update(film);
	}

	@Override
	public Film removeFilm(Integer id) {
		throwNewEntityNotFoundException(id);
		log.info("REMOVE FILM HAS ARRIVED BY ID '{}'", id);
		return repository.removeById(id);
	}

	@Override
	public void cleaner() {
		log.warn("REPOSITORY CLEARED");
		repository.cleaner();
	}

	private void throwNewEntityNotFoundException(Integer id) {
		if (id == null) {
			throw new EntityNotFoundException("Film id is Null");
		}
		if (repository.findById(id) == null) {
			throw new EntityNotFoundException("Film not found by id " + id);
		}
	}
}
