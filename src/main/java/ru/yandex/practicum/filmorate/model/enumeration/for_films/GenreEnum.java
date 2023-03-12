package ru.yandex.practicum.filmorate.model.enumeration.for_films;

public enum GenreEnum {
    COMEDY("Комедия"),
    DRAMA("Драма"),
    ANIMATED("Мультфильм"),
    THRILLER("Триллер"),
    DOCUMENTARY("Документальный"),
    ACTION_FILM("Боевик");

    private final String title;

    GenreEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
