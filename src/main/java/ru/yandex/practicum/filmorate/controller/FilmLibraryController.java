package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.projection.InteractionServiceWFLInterface;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FilmLibraryController {

	private final InteractionServiceWFLInterface interactionService;

	@PutMapping("/films/{id}/like/{userId}")
	public ResponseEntity<Film> like(@PathVariable("id") Integer filmId,
									 @PathVariable Integer userId) {
		return ResponseEntity.ok(interactionService.putLike(filmId, userId));
	}

	@DeleteMapping("/films/{id}/like/{userId}")
	public ResponseEntity<Film> removeLike(@PathVariable("id") Integer filmId,
										   @PathVariable Integer userId) {
		return ResponseEntity.ok(interactionService.removeLike(filmId, userId));
	}

	@GetMapping("/films/popular")
	public ResponseEntity<List<Film>> bringBackPopularFilms(@RequestParam(defaultValue = "10")
															Integer count) {
		return ResponseEntity.ok(interactionService.popularFilms(count));
	}

	@GetMapping("/mpa")
	public ResponseEntity<List<MPA>> bringBackMpaList() {
		return ResponseEntity.ok(interactionService.MPAList());
	}

	@GetMapping("/mpa/{id}")
	public ResponseEntity<MPA> bringBackMpa(@PathVariable Integer id) {
		System.out.println("id - " + id);
		return ResponseEntity.ok(interactionService.MPAbyId(id));
	}

	@GetMapping("/genres")
	public ResponseEntity<List<Genre>> bringBackGenresList() {
		return ResponseEntity.ok(interactionService.genresList());
	}

	@GetMapping("/genres/{id}")
	public ResponseEntity<Genre> bringBackGenre(@PathVariable Integer id) {
		return ResponseEntity.ok(interactionService.genreById(id));
	}
}
