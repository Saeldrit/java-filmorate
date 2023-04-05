package ru.yandex.practicum.filmorate.exception.controller_advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exception.GenresListEmptyException;
import ru.yandex.practicum.filmorate.exception.InvalidValueForEqualsException;
import ru.yandex.practicum.filmorate.exception.MpaListEmptyException;
import ru.yandex.practicum.filmorate.exception.sample_response.ErrorResponse;

@RestControllerAdvice("ru.yandex.practicum.filmorate.controller")
public class ErrorHandler {

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handlerDefaultException(
			Exception exception) {
		return new ErrorResponse("exception", exception.getMessage());
	}

	@ExceptionHandler({
			EntityNotFoundException.class,
			MpaListEmptyException.class,
			GenresListEmptyException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleNotFoundException(
			RuntimeException exception) {
		return new ErrorResponse("error", exception.getMessage());
	}

	@ExceptionHandler({
			MethodArgumentNotValidException.class,
			InvalidValueForEqualsException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleValidationException(
			RuntimeException exception) {
		return new ErrorResponse("validation", exception.getMessage());
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleArgumentException(
			IllegalArgumentException exception) {
		return new ErrorResponse("argument", exception.getMessage());
	}
}
