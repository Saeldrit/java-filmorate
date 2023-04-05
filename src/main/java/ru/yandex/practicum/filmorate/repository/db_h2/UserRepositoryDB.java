package ru.yandex.practicum.filmorate.repository.db_h2;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.abstraction.AbstractFabricUserRepository;
import ru.yandex.practicum.filmorate.sql_query.SqlQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static java.sql.Date.valueOf;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
public class UserRepositoryDB extends AbstractFabricUserRepository<Integer, User> {

	private final SqlQuery userQuery;
	private final JdbcTemplate jdbcTemplate;

	@Override
	public User save(User user) {
		User usWithName = ifThereIsNoNameSetLogin(user);
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(
					userQuery.insert(),
					new String[]{"id"});
			ps.setString(1, (usWithName.getEmail()));
			ps.setString(2, usWithName.getLogin());
			ps.setString(3, usWithName.getName());
			ps.setDate(4, valueOf(usWithName.getBirthday()));
			return ps;
		}, keyHolder);

		int id = requireNonNull(keyHolder.getKey()).intValue();

		return findById(id);
	}

	@Override
	public Collection<User> bringBackAllValues() {
		Map<Integer, User> userMap = new HashMap<>();

		jdbcTemplate.query(userQuery.selectAllValues(), rs -> {
			int id = rs.getInt("id");

			User user = userMap.get(id);

			if (user == null) {
				user = collectUser(rs);
				userMap.put(id, user);
			}
		});

		return new ArrayList<>(userMap.values());
	}

	@Override
	public User findById(Integer id) {
		return jdbcTemplate.query(userQuery.selectById(), new Object[]{id}, rs -> {
			User user = null;

			if (rs.next()) {
				user = collectUser(rs);
			}
			return user;
		});
	}

	@Override
	public User removeById(Integer id) {
		User user = findById(id);

		if (user != null) {
			jdbcTemplate.update(userQuery.deleteById(), id);
		}
		return user;
	}

	@Override
	public User update(User entity) {
		jdbcTemplate.update(userQuery.update()
				, entity.getEmail()
				, entity.getName()
				, entity.getLogin()
				, entity.getBirthday()
				, entity.getId());
		return findById(entity.getId());
	}

	@Override
	public Collection<User> returnFriendsByUser(User user) {
		SqlRowSet set = jdbcTemplate.queryForRowSet(
				userQuery.returnFriendsByUserId(),
				user.getId());

		List<User> userList = new ArrayList<>();

		while (set.next()) {
			userList.add(buildUserFromRowSet(set));
		}
		return userList;
	}

	@Override
	public User addTo(Integer userId, Integer friendId) {
		User user = findById(userId);
		if (addFriend(userId, friendId) > 0) {
			return user;
		}
		return null;
	}

	@Override
	public User removeFrom(Integer userId, Integer friendId) {
		User user = findById(userId);
		if (removeFriend(userId, friendId) > 0) {
			return user;
		}
		return null;
	}

	@Override
	public Collection<User> returnCommonFriends(Integer userId, Integer friendId) {
		return jdbcTemplate.query(userQuery.mutualFriends(),
				new Object[]{userId, friendId},
				(rs, rowNum) -> collectUser(rs));
	}

	private int addFriend(Integer userId, Integer friendId) {
		return jdbcTemplate.update(
				userQuery.addFriend(), userId, friendId);
	}

	private int removeFriend(Integer userId, Integer friendId) {
		return jdbcTemplate.update(
				userQuery.removeFriend(), userId, friendId);
	}

	private User buildUserFromRowSet(SqlRowSet rowSet) {
		return new User(
				rowSet.getInt("id"),
				rowSet.getString("email"),
				rowSet.getString("login"),
				rowSet.getString("name"),
				Objects.requireNonNull(
								rowSet.getDate("birthday"))
						.toLocalDate(),
				Collections.emptySet(),
				Collections.emptySet()
		);
	}

	private User collectUser(ResultSet rs) throws SQLException {
		return User.builder()
				.id(rs.getInt("id"))
				.email(rs.getString("email"))
				.login(rs.getString("login"))
				.name(rs.getString("name"))
				.birthday(rs.getDate("birthday").toLocalDate())
				.build();
	}

	private User ifThereIsNoNameSetLogin(User user) {
		return user.getName() != null &&
				!user.getName().isEmpty() ?
				user :
				user.withName(user.getLogin());
	}
}
