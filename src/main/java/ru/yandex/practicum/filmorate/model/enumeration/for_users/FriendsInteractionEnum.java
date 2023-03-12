package ru.yandex.practicum.filmorate.model.enumeration.for_users;

public enum FriendsInteractionEnum {
    IS_FRIEND("Друзья"),
    IS_NIT_FRIEND("Не друзья");

    private final String title;

    FriendsInteractionEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
