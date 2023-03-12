package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.memory_repository.FilmRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;

class FilmServiceTest {

    private final FilmService service =
            new FilmService(
                    new FilmRepository());

    @BeforeEach
    void beforeEach() {
        service.addNewFilm(buildMovie());
    }

    private Film buildMovie() {
        return Film.builder()
                .name("Stalker")
                .description("Description")
                .releaseDate(LocalDate.of(1984, 12, 25))
                .duration(100L)
                .build();
    }

    @AfterEach
    void afterEach() {
        service.cleaner();
    }

    @DisplayName("Создать фильм без id и вернуть объект с id")
    @Test
    void shouldCreateNewMovie_whenCallMethod_thenReturnThisMovie() {
        Film movieWithoutId = buildMovie();

        Film movieWithId = service.addNewFilm(movieWithoutId);
        assertNotNull(movieWithId.getId());
    }

    @DisplayName("Не создавать новый фильм, если такой есть")
    @Test
    void shouldNotCreateNewMovie_ifThereIsOne() {
        Film movie = buildMovie();
        service.addNewFilm(movie);

        assertEquals(1, service.returnAllFilms().size());
    }

    @DisplayName("Вернуть фильм, который мы создали")
    @Test
    void shouldBringBackTheMovieWeCreated() {
        Film movie = buildMovie();

        assertEquals(movie, service.findFilmById(1));
    }

    @DisplayName("Вернуть фильм по id 1")
    @Test
    void shouldReturnFilm_whenItIsFoundById() {
        assertNotNull(service.findFilmById(1));
    }

    @DisplayName("EntityNotFound если фильм не найден")
    @Test
    void shouldThrowAnError_whenTheMovieIsNotFound_ifTheIdDoesNotExist() {
        int movieId = anyInt();
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class,
                        () -> service.findFilmById(movieId));

        assertEquals("Film not found by id " + movieId, exception.getMessage());
    }

    @DisplayName("Обновить фильм если значения полей поменялись")
    @Test
    void shouldUpdateMovie_whenFieldsChanged() {
        Film film = service.findFilmById(1);

        Film filmForUpdate = Film.builder()
                .id(film.getId())
                .name("Stalker 2")
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(101L)
                .build();

        Film filmAfterUpdate = service.updateFilm(filmForUpdate);

        assertAll(() -> assertNotEquals(film, filmAfterUpdate),
                () -> assertNotEquals(film.getName(), filmAfterUpdate.getName()),
                () -> assertNotEquals(film.getDuration(), filmAfterUpdate.getDuration()),
                () -> assertEquals(film.getReleaseDate(), filmAfterUpdate.getReleaseDate()),
                () -> assertEquals(1, service.returnAllFilms().size()));
    }

    @DisplayName("EntityNorFound при обновлении")
    @Test
    void shouldThrowNAnError_whenUpdateMovieIsNotFound() {
        Film film = service.findFilmById(1);

        Film filmForUpdate = Film.builder()
                .id(film.getId() + 1)
                .name("Stalker 2")
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(101L)
                .build();

        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class,
                        () -> service.updateFilm(filmForUpdate));

        assertEquals("Film not found by id "
                        + filmForUpdate.getId(),
                exception.getMessage());
    }

    @DisplayName("Вернуть фильм, который мы удалили")
    @Test
    void shouldReturnMovieThatWasDeleted() {
        Film filmAfterRemoving = service.removeFilm(1);

        assertNotNull(filmAfterRemoving);
        assertTrue(service.returnAllFilms().isEmpty());
    }
}