package ru.yandex.practicum.filmorate.sql_query;

public interface SqlQuery {
	String insert();

	String selectById();

	String selectAllValues();

	String deleteById();

	String update();

	String like();

	String dropTable();

	default String removeLike() {
		return "";
	}

	default String selectPopular() {
		return "";
	}

	default String insertGenreFilmId() {
		return "";
	}

	default String returnFriendsByUserId() {
		return "";
	}

	default String addFriend() {
		return "";
	}

	default String removeFriend() {
		return "";
	}

	default String insertMPA() {
		return "";
	}

	default String insertGenre() {
		return "";
	}

	default String deleteFromFilmGenre() {
		return "";
	}

	default String selectMPA() {
		return "";
	}

	default String selectListMPA() {
		return "";
	}

	default String selectGenre() {
		return "";
	}

	default String selectListGenres() {
		return "";
	}
	default String selectGenresByFilmId() {
		return "";
	}

	default String mutualFriends() {
		return "";
	}
}
