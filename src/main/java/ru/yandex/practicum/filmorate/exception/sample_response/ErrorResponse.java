package ru.yandex.practicum.filmorate.exception.sample_response;

import lombok.Value;

@Value
public class ErrorResponse {
    String error;
    String description;
}
