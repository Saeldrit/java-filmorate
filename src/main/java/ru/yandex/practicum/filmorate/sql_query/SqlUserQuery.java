package ru.yandex.practicum.filmorate.sql_query;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("userQuery")
public class SqlUserQuery implements SqlQuery {

	@Value("${user.insert-user}")
	private String insert;

	@Value("${user.select-users}")
	private String selectUsers;

	@Value("${user.select-by-id}")
	private String selectById;

	@Value("${user.delete}")
	private String delete;

	@Value("${user.update-user}")
	private String update;

	@Value("${user.friends-by-user-id}")
	private String friends;

	@Value("${user.insert-friend}")
	private String insertFriend;

	@Value("${user.remove-friend}")
	private String removeFriend;

	@Value("${user.select-mutual-friends}")
	private String mutualFriends;

	@Override
	public String insert() {
		return insert;
	}

	@Override
	public String selectById() {
		return selectById;
	}

	@Override
	public String selectAllValues() {
		return selectUsers;
	}

	@Override
	public String deleteById() {
		return delete;
	}

	@Override
	public String update() {
		return update;
	}

	@Override
	public String returnFriendsByUserId() {
		return friends;
	}

	@Override
	public String addFriend() {
		return insertFriend;
	}

	@Override
	public String removeFriend() {
		return removeFriend;
	}

	@Override
	public String like() {
		return null;
	}

	@Override
	public String mutualFriends() {
		return mutualFriends;
	}
}
