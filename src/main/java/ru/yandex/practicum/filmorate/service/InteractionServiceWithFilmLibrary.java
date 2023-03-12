package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import java.security.GeneralSecurityException;
import java.util.List;

@Slf4j
@Service
public class InteractionServiceWithFilmLibrary implements InteractionServiceWFLInterface {

	private final AbstractFabricFilmRepository<Integer, Film> filmRepository;
	private final AbstractFabricUserRepository<Integer, User> userRepository;

	@Autowired
	public InteractionServiceWithFilmLibrary(@Qualifier("filmRepositoryDB")
											 AbstractFabricFilmRepository<Integer, Film> filmRepository,
											 @Qualifier("userRepositoryDB")
											 AbstractFabricUserRepository<Integer, User> userRepository) {
		this.filmRepository = filmRepository;
		this.userRepository = userRepository;
	}

	public Film putLike(Integer filmId, Integer userId) {
		log.info("putLike() argument film ID '{}', user ID '{}'", filmId, userId);
		throwNewEntityNotFoundException(filmId, userId);

		userRepository.like(filmId, userId);
		return filmRepository.like(filmId, userId);
	}

	public Film removeLike(Integer filmId, Integer userId) {
		log.info("removeLike() film id '{}', user id '{}'", filmId, userId);
		throwNewEntityNotFoundException(filmId, userId);

		userRepository.removeLike(filmId, userId);
		return filmRepository.removeLike(filmId, userId);
	}

	@Override
	public List<Film> popularFilms(Integer count) {
		return filmRepository.returnPopularFilms(count);
	}

	@Override
	public MPA MPAbyId(Integer id) {
		MPA mpa = filmRepository.MPAById(id);
		throwNewMPANotFoundException(mpa);
		return mpa;
	}

	@Override
	public List<MPA> MPAList() {
		List<MPA> mpaList = filmRepository.listMPA();
		if (mpaList.isEmpty()) {
			throw new MpaListEmptyException("Mpa list is empty");
		}
		return mpaList;
	}

	@Override
	public Genre genreById(Integer id) {
		Genre genre = filmRepository.genreById(id);
		throwNewGenreNotFoundException(genre);
		return genre;
	}

	@Override
	public List<Genre> genresList() {
		List<Genre> genres = filmRepository.listGenres();
		if (genres.isEmpty()) {
			throw new GenresListEmptyException("Genres list is empty");
		}
		return genres;
	}

	private void throwNewEntityNotFoundException(Integer filmId, Integer userId) {
		if (filmRepository.findById(filmId) == null) {
			throw new EntityNotFoundException("Film not found by id " + filmId);
		}
		if (userRepository.findById(userId) == null) {
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
