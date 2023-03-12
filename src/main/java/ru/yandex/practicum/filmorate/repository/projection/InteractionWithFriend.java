package ru.yandex.practicum.filmorate.repository.projection;

import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface InteractionWithFriend<I, T> extends InterfaceCRUD<I, T> {

    Collection<T> returnFriendsByUser(T user);

    T addTo(I userId, I friendId);

    T removeFrom(I userId, I friendId);

    Collection<T> returnCommonFriends(I userId, I friendId);
}
