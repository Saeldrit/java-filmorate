package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.projection.InterfaceCRUD;
import ru.yandex.practicum.filmorate.service.projection.UserServiceInterface;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

	private final InterfaceCRUD<Integer, User> userRepositoryDB;

	@Override
	public List<User> returnAllUsers() {
		return new ArrayList<>(userRepositoryDB.bringBackAllValues());
	}

	@Override
	public User addNewUser(User user) {
		log.info("addNewUser() attempt to create '{}'", user.getName());
		return userRepositoryDB.save(user);
	}

	@Override
	public User findUserById(Integer id) {
		throwNewEntityNotFoundException(id);

		log.info("findUserById() id '{}'", id);
		return userRepositoryDB.findById(id);
	}

	@Override
	public User updateUser(User user) {
		throwNewEntityNotFoundException(user.getId());

		log.info("updateUser() has arrived id '{}'", user.getId());
		return userRepositoryDB.update(user);
	}

	@Override
	public User removeUser(Integer id) {
		throwNewEntityNotFoundException(id);

		log.info("removeUser() has arrived id '{}'", id);
		return userRepositoryDB.removeById(id);
	}

	@Override
	public void cleaner() {
		log.info("Repository cleared");
		userRepositoryDB.cleaner();
	}

	private void throwNewEntityNotFoundException(Integer id) {
		if (id == null) {
			log.warn("User Id is null");
			throw new EntityNotFoundException("User id is null");
		}
		if (userRepositoryDB.findById(id) == null) {
			log.warn("Find user by id {} is null", id);
			throw new EntityNotFoundException("User not found by id " + id);
		}
	}
}
