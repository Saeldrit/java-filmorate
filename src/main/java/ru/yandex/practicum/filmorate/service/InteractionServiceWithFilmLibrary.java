package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exception.GenresListEmptyException;
import ru.yandex.practicum.filmorate.exception.MpaListEmptyException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.abstraction.AbstractFabricFilmRepository;
import ru.yandex.practicum.filmorate.repository.abstraction.AbstractFabricUserRepository;
import ru.yandex.practicum.filmorate.service.projection.InteractionServiceWFLInterface;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InteractionServiceWithFilmLibrary implements InteractionServiceWFLInterface {

	private final AbstractFabricFilmRepository<Integer, Film> filmRepositoryDB;
	private final AbstractFabricUserRepository<Integer, User> userRepositoryDB;

	public Film putLike(Integer filmId, Integer userId) {
		log.info("putLike() argument film ID '{}', user ID '{}'", filmId, userId);
		throwNewEntityNotFoundException(filmId, userId);

		userRepositoryDB.like(filmId, userId);
		return filmRepositoryDB.like(filmId, userId);
	}

	public Film removeLike(Integer filmId, Integer userId) {
		log.info("removeLike() film id '{}', user id '{}'", filmId, userId);
		throwNewEntityNotFoundException(filmId, userId);

		userRepositoryDB.removeLike(filmId, userId);
		return filmRepositoryDB.removeLike(filmId, userId);
	}

	@Override
	public List<Film> popularFilms(Integer count) {
		return filmRepositoryDB.returnPopularFilms(count);
	}

	@Override
	public MPA MPAbyId(Integer id) {
		MPA mpa = filmRepositoryDB.MPAById(id);
		throwNewMPANotFoundException(mpa);
		return mpa;
	}

	@Override
	public List<MPA> MPAList() {
		List<MPA> mpaList = filmRepositoryDB.listMPA();
		if (mpaList.isEmpty()) {
			throw new MpaListEmptyException("Mpa list is empty");
		}
		return mpaList;
	}

	@Override
	public Genre genreById(Integer id) {
		Genre genre = filmRepositoryDB.genreById(id);
		throwNewGenreNotFoundException(genre);
		return genre;
	}

	@Override
	public List<Genre> genresList() {
		List<Genre> genres = filmRepositoryDB.listGenres();
		if (genres.isEmpty()) {
			throw new GenresListEmptyException("Genres list is empty");
		}
		return genres;
	}

	private void throwNewEntityNotFoundException(Integer filmId, Integer userId) {
		if (filmRepositoryDB.findById(filmId) == null) {
			throw new EntityNotFoundException("Film not found by id " + filmId);
		}
		if (userRepositoryDB.findById(userId) == null) {
			throw new EntityNotFoundException("User not found by id " + userId);
		}
	}

	private void throwNewMPANotFoundException(MPA mpa) {
		if (mpa == null) {
			throw new EntityNotFoundException("MPA not found, entity is null");
		}
	}

	private void throwNewGenreNotFoundException(Genre genre) {
		if (genre == null) {
			throw new EntityNotFoundException("Genre not found, entity is null");
		}
	}
}
