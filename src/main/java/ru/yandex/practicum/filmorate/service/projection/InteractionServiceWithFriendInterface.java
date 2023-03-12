package ru.yandex.practicum.filmorate.service.projection;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface InteractionServiceWithFriendInterface {
    User addToFriend(Integer id, Integer friendId);

    User removeFriend(Integer id, Integer friendId);

    List<User> returnAllFriends(Integer id);

    List<User> returnCommonFriends(Integer id, Integer otherId);
}
