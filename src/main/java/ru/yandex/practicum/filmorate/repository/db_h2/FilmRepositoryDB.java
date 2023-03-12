package ru.yandex.practicum.filmorate.repository.db_h2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.enumeration.for_films.MPAEnum;
import ru.yandex.practicum.filmorate.repository.abstraction.AbstractFabricFilmRepository;
import ru.yandex.practicum.filmorate.sql_query.SqlQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.sql.Date.valueOf;
import static java.util.Objects.requireNonNull;

@Slf4j
@Repository
public class FilmRepositoryDB extends AbstractFabricFilmRepository<Integer, Film> {

	private final SqlQuery sqlQuery;

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public FilmRepositoryDB(@Qualifier("filmQuery") SqlQuery sqlQuery,
							JdbcTemplate jdbcTemplate) {
		this.sqlQuery = sqlQuery;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Film save(Film film) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(
					sqlQuery.insert(),
					new String[]{"id"});
			ps.setString(1, film.getName());
			ps.setString(2, film.getDescription());
			ps.setDate(3, valueOf(film.getReleaseDate()));
			ps.setLong(4, film.getDuration());
			ps.setInt(5, film.getRate());
			ps.setInt(6, film.getMpa().getId());
			return ps;
		}, keyHolder);

		int id = requireNonNull(keyHolder.getKey()).intValue();

		Film filmWithId = film.withId(id);
		saveGenresForFilm(filmWithId);

		return findById(id);
	}

	@Override
	public List<Film> bringBackAllValues() {
		Map<Integer, Film> filmMap = new HashMap<>();

		jdbcTemplate.query(sqlQuery.selectAllValues(), rs -> {
			int filmId = rs.getInt("id");

			Film film = filmMap.get(filmId);

			if (film == null) {
				film = collectFilm(rs);
				filmMap.put(filmId, film);
			}
			int genreId = rs.getInt("genre_id");

			if (genreId != 0) {
				Genre genre = new Genre(genreId, rs.getString("genre_name"));
				film.getGenres().add(genre);
			}
		});
		return new ArrayList<>(filmMap.values());
	}

	@Override
	public Film findById(Integer id) {
		return jdbcTemplate.query(sqlQuery.selectById(), new Object[]{id}, rs -> {
			Film film = null;

			while (rs.next()) {
				if (film == null) {
					film = collectFilm(rs);
				}
				int genreId = rs.getInt("genre_id");

				if (genreId != 0) {
					Genre genre = new Genre(genreId, rs.getString("genre_name"));
					film.getGenres().add(genre);
				}
			}
			return film;
		});
	}

	@Override
	public Film removeById(Integer id) {
		Film film = findById(id);

		if (film != null) {
			jdbcTemplate.update(sqlQuery.deleteFromFilmGenre(), id);
			jdbcTemplate.update(sqlQuery.deleteById(), id);
		}
		return film;
	}

	@Override
	public Film update(Film entity) {
		List<Genre> genres = updateGenresForFilm(entity);
		genres.forEach(e -> System.out.println(e.getName()));
		entity.setGenres(genres);

		jdbcTemplate.update(sqlQuery.update()
				, entity.getName()
				, entity.getDescription()
				, entity.getReleaseDate()
				, entity.getDuration()
				, entity.getRate()
				, entity.getMpa().getId()
				, entity.getId());

		return findById(entity.getId());
	}

	@Override
	public void cleaner() {
		jdbcTemplate.update(sqlQuery.dropTable());
	}

	@Override
	public Film like(Integer filmId, Integer userId) {
		jdbcTemplate.update(sqlQuery.like(), filmId, userId);
		return findById(filmId);
	}

	@Override
	public Film removeLike(Integer filmId, Integer userId) {
		jdbcTemplate.update(sqlQuery.removeLike(), filmId, userId);
		return findById(filmId);
	}

	@Override
	public List<Film> returnPopularFilms(Integer count) {
		Map<Integer, Film> filmMap = new HashMap<>();

		jdbcTemplate.query(sqlQuery.selectPopular(), new Object[]{count}, rs -> {
			int id = rs.getInt("id");
			Film film = filmMap.get(id);

			if (film == null) {
				film = collectFilm(rs);
				filmMap.put(id, film);
			}
		});
		return new ArrayList<>(filmMap.values());
	}

	@Override
	public MPA MPAById(Integer id) {
		return jdbcTemplate.query(sqlQuery.selectMPA(), new Object[]{id}, rs -> {
			MPA mpa = null;

			if (rs.next()) {
				mpa = collectMPA(rs);
			}
			return mpa;
		});
	}

	@Override
	public Genre genreById(Integer id) {
		return jdbcTemplate.query(sqlQuery.selectGenre(), new Object[]{id}, rs -> {
			Genre genre = null;

			if (rs.next()) {
				genre = collectGenre(rs);
			}
			return genre;
		});
	}

	@Override
	public List<MPA> listMPA() {
		Map<Integer, MPA> mpaMap = new HashMap<>();

		jdbcTemplate.query(sqlQuery.selectListMPA(), rs -> {
			int mpaId = rs.getInt("id");

			MPA mpa = mpaMap.get(mpaId);

			if (mpa == null) {
				mpa = collectMPA(rs);
				mpaMap.put(mpaId, mpa);
			}
		});
		return new ArrayList<>(mpaMap.values());
	}

	@Override
	public List<Genre> listGenres() {
		Map<Integer, Genre> genreMap = new HashMap<>();

		jdbcTemplate.query(sqlQuery.selectListGenres(), rs -> {
			int genreId = rs.getInt("id");

			Genre genre = genreMap.get(genreId);

			if (genre == null) {
				genre = collectGenre(rs);
				genreMap.put(genreId, genre);
			}
		});
		return new ArrayList<>(genreMap.values());
	}

	private MPA collectMPA(ResultSet rs) throws SQLException {
		return MPA.builder()
				.id(rs.getInt("id"))
				.name(rs.getString("name"))
				.build();
	}

	private Genre collectGenre(ResultSet rs) throws SQLException {
		return Genre.builder()
				.id(rs.getInt("id"))
				.name(rs.getString("name"))
				.build();
	}

	private Film collectFilm(ResultSet rs) throws SQLException {
		int mpaId = rs.getInt("mpa_id");
		return Film.builder()
				.id(rs.getInt("id"))
				.name(rs.getString("name"))
				.description(rs.getString("description"))
				.releaseDate(rs.getDate("release_date").toLocalDate())
				.duration(rs.getLong("duration"))
				.rate(rs.getInt("rate"))
				.mpa(new MPA(mpaId, MPAEnum.values()[mpaId - 1].getTitle()))
				.genres(new ArrayList<>())
				.build();
	}

	private void saveGenresForFilm(Film film) {
		for (Genre genre : film.getGenres()) {
			try {
				jdbcTemplate.update(sqlQuery.insertGenreFilmId(),
						film.getId(), genre.getId());
			} catch (DataIntegrityViolationException e) {
				log.warn("Duplicate pair film.id / genre.id {}, {}", film.getId(), genre.getId());
			}
		}
	}

	private List<Genre> updateGenresForFilm(Film film) {
		jdbcTemplate.update(sqlQuery.deleteFromFilmGenre(), film.getId());

		saveGenresForFilm(film);

		Map<Integer, Genre> genreMap = new HashMap<>();

		jdbcTemplate.query(sqlQuery.selectGenresByFilmId(), new Object[]{film.getId()}, rs -> {
			int genreId = rs.getInt("id");

			Genre genre = genreMap.get(genreId);

			if (genre == null) {
				genre = collectGenre(rs);
				genreMap.put(genreId, genre);
			}
		});
		return new ArrayList<>(genreMap.values());
	}
}
