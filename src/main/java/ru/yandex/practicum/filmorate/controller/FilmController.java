package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.projection.FilmServiceInterface;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
	private final FilmServiceInterface filmService;

	@PostMapping()
	public ResponseEntity<Film> addNewFilm(@Valid @RequestBody Film film) {
		return ResponseEntity.ok(filmService.addNewFilm(film));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Film> findFilmById(@PathVariable Integer id) {
		return ResponseEntity.ok(filmService.findFilmById(id));
	}

	@GetMapping
	public ResponseEntity<List<Film>> returnAllFilms() {
		return ResponseEntity.ok(filmService.returnAllFilms());
	}

	@PutMapping
	public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
		return ResponseEntity.ok(filmService.updateFilm(film));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Film> removeFilm(@PathVariable Integer id) {
		return ResponseEntity.ok(filmService.removeFilm(id));
	}
}
