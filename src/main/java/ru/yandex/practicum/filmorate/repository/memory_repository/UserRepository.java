package ru.yandex.practicum.filmorate.repository.memory_repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.abstraction.AbstractFabricUserRepository;

import java.util.*;

@Slf4j
@Repository
public class UserRepository extends AbstractFabricUserRepository<Integer, User> {

    private Integer intermediateId = 0;

    protected Integer generateId() {
        return ++intermediateId;
    }

    @Override
    public User save(User user) {
        if (searchCopy(user)) {
            User userWithId = ifThereIsNoIdGenerateIt(user);
            User userWithName = ifThereIsNoNameSetLogin(userWithId);
            getObjectMap().putIfAbsent(userWithName.getId(), userWithName);
        }

        return getObjectMap().get(intermediateId);
    }

    @Override
    public User update(User user) {
        if (getObjectMap().containsKey(user.getId())) {
            getObjectMap().put(user.getId(), user);

            return getObjectMap().get(user.getId());
        }
        return null;
    }

    @Override
    public Collection<User> returnFriendsByUser(User user) {
        Set<Integer> iSet = user.getFriendSet();
        log.warn("returnUsersByListId() - userIds isEmpty() '{}'", iSet.isEmpty());
        List<User> users = new ArrayList<>();
        iSet.forEach(id -> users.add(getObjectMap().get(id)));
        return users;
    }

    @Override
    public User addTo(Integer userId, Integer friendId) {
        User user = getObjectMap().get(userId);
        user.addToFriendSet(friendId);
        return update(user);
    }

    @Override
    public User removeFrom(Integer userId, Integer friendId) {
        User user = getObjectMap().get(userId);
        user.removeFromFriendSet(friendId);
        return update(user);
    }

    @Override
    public User like(Integer filmId, Integer userId) {
        User user = getObjectMap().get(userId);
        user.addToLikesSet(filmId);
        return update(user);
    }

    @Override
    public User removeLike(Integer friendId, Integer userId) {
        User user = getObjectMap().get(userId);
        user.removeFromLikesSet(friendId);
        return update(user);
    }

    @Override
    public Collection<User> returnCommonFriends(Integer useId, Integer friendId) {
        List<User> users = new ArrayList<>();
        Set<Integer> userIds = new HashSet<>(findById(useId).getFriendSet());
        Set<Integer> friendIds = new HashSet<>(findById(friendId).getFriendSet());
        userIds.retainAll(friendIds);
        userIds.forEach(i -> users.add(findById(i)));
        return users;
    }

    private User ifThereIsNoNameSetLogin(User user) {
        return user.getName() != null &&
                !user.getName().isEmpty() ?
                user :
                user.withName(user.getLogin());
    }

    private User ifThereIsNoIdGenerateIt(User user) {
        return user.getId() != null ?
                user :
                user.withId(generateId());
    }
}
