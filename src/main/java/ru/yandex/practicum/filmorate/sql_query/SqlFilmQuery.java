package ru.yandex.practicum.filmorate.sql_query;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("filmQuery")
public class SqlFilmQuery implements SqlQuery {

	@Value("${film.insert-film}")
	private String insertFilmQuery;

	@Value("${film.select-film-by-id}")
	private String selectFilmById;

	@Value("${film.select-films}")
	private String selectFilms;

	@Value("${film.delete-by-id}")
	private String deleteById;

	@Value("${film.update-film}")
	private String update;

	@Value("${film.like}")
	private String like;

	@Value("${film.remove-like}")
	private String removeLike;

	@Value("${film.select-popular}")
	private String popular;

	@Value("${film.insert-genre-film-id}")
	private String insertGenreFilmId;

	@Value("${film.insert-mpa}")
	private String initMpa;

	@Value("${film.insert-genres}")
	private String initGenre;

	@Value("${film.delete-film-genre}")
	private String deleteFromFilmGenre;

	@Value("${film.select-mpa}")
	private String mpa;

	@Value("${film.select-list-mpa}")
	private String listMpa;

	@Value("${film.select-genre}")
	private String genre;

	@Value("${film.select-list-genres}")
	private String listGenre;

	@Value("${film.select-genres-by-film-id}")
	private String genresByFilmId;

	@Override
	public String insert() {
		return insertFilmQuery;
	}

	@Override
	public String selectById() {
		return selectFilmById;
	}

	@Override
	public String selectAllValues() {
		return selectFilms;
	}

	@Override
	public String deleteById() {
		return deleteById;
	}

	@Override
	public String update() {
		return update;
	}

	@Override
	public String like() {
		return like;
	}

	@Override
	public String removeLike() {
		return removeLike;
	}

	@Override
	public String selectPopular() {
		return popular;
	}

	@Override
	public String insertGenreFilmId() {
		return insertGenreFilmId;
	}

	@Override
	public String insertMPA() {
		return initMpa;
	}

	@Override
	public String insertGenre() {
		return initGenre;
	}

	@Override
	public String deleteFromFilmGenre() {
		return deleteFromFilmGenre;
	}

	@Override
	public String selectMPA() {
		return mpa;
	}

	@Override
	public String selectListMPA() {
		return listMpa;
	}

	@Override
	public String selectGenre() {
		return genre;
	}

	@Override
	public String selectListGenres() {
		return listGenre;
	}

	@Override
	public String selectGenresByFilmId() {
		return genresByFilmId;
	}
}
