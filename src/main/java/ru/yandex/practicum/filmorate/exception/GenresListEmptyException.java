package ru.yandex.practicum.filmorate.exception;

public class GenresListEmptyException extends RuntimeException {
	public GenresListEmptyException(String message) {
		super(message);
	}
}
