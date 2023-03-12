package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exception.InvalidValueForEqualsException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.abstraction.AbstractFabricUserRepository;
import ru.yandex.practicum.filmorate.service.projection.InteractionServiceWithFriendInterface;

import java.util.*;

@Slf4j
@Service
public class InteractionUserWithFriend implements InteractionServiceWithFriendInterface {

	private final AbstractFabricUserRepository<Integer, User> repository;

	@Autowired
	public InteractionUserWithFriend(
			@Qualifier("userRepositoryDB")
			AbstractFabricUserRepository<Integer, User> repository) {
		this.repository = repository;
	}

	@Override
	public User addToFriend(Integer id, Integer friendId) {
		ifIdsAreEqualsThrowNewException(id, friendId);

		throwNewEntityNotFoundException(id);
		throwNewEntityNotFoundException(friendId);

		log.info("addToFriend() USER ID '{}', FRIEND ID '{}'", id, friendId);

		return repository.addTo(id, friendId);
	}

	@Override
	public User removeFriend(Integer id, Integer friendId) {
		ifIdsAreEqualsThrowNewException(id, friendId);

		throwNewEntityNotFoundException(id);
		throwNewEntityNotFoundException(friendId);

		log.info("removeFriend() USER ID '{}', FRIEND ID '{}'", id, friendId);
		User userDeletedFriend = repository.removeFrom(id, friendId);

		repository.removeFrom(friendId, id);
		return userDeletedFriend;
	}

	@Override
	public List<User> returnAllFriends(Integer id) {
		throwNewEntityNotFoundException(id);

		log.info("returnAllFriends() USER ID '{}'", id);

		User user = repository.findById(id);
		return new ArrayList<>(repository.returnFriendsByUser(user));
	}

	@Override
	public List<User> returnCommonFriends(Integer id, Integer friendId) {
		ifIdsAreEqualsThrowNewException(id, friendId);

		throwNewEntityNotFoundException(id);
		throwNewEntityNotFoundException(friendId);

		return new ArrayList<>(repository.returnCommonFriends(id, friendId));
	}

	private void ifIdsAreEqualsThrowNewException(Integer id, Integer friendId) {
		if (id.equals(friendId)) {
			throw new InvalidValueForEqualsException(
					String.format("USER ID %d EQUALS FRIEND ID %d", id, friendId)
			);
		}
	}

	private void throwNewEntityNotFoundException(Integer id) {
		if (id == null) {
			throw new EntityNotFoundException("User id is null");
		}
		if (repository.findById(id) == null) {
			throw new EntityNotFoundException("User not found by id " + id);
		}
	}
}
