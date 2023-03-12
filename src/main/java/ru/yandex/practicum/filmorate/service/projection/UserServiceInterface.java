package ru.yandex.practicum.filmorate.service.projection;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Service
public interface UserServiceInterface {
    List<User> returnAllUsers();

    User addNewUser(User user);

    User findUserById(Integer id);

    User updateUser(User user);

    User removeUser(Integer id);

    void cleaner();
}
