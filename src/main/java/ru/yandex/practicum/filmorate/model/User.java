package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.NonFinal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(exclude = {"id", "friendSet", "filmsLikes"})
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class User {

	Integer id;

	@Email
	String email;

	@NotBlank
	String login;

	String name;

	@Past
	LocalDate birthday;

	@JsonIgnore
	@NonFinal
	Set<Integer> friendSet;

	@JsonIgnore
	@NonFinal
	Set<Integer> filmsLikes;

	public boolean addToFriendSet(Integer friendId) {
		if (friendSet == null) {
			this.friendSet = new HashSet<>();
		}
		return friendSet.add(friendId);
	}

	public boolean addToLikesSet(Integer filmId) {
		if (filmsLikes == null) {
			this.filmsLikes = new HashSet<>();
		}
		return filmsLikes.add(filmId);
	}

	public Set<Integer> getFriendSet() {
		return this.friendSet != null ?
				this.friendSet :
				(this.friendSet = new HashSet<>());
	}

	public Set<Integer> getFilmsLikes() {
		return filmsLikes != null ?
				this.filmsLikes :
				(this.filmsLikes = new HashSet<>());
	}

	public boolean removeFromFriendSet(Integer friendId) {
		if (this.friendSet == null) {
			this.friendSet = new HashSet<>();
		}
		return friendSet.remove(friendId);
	}

	public boolean removeFromLikesSet(Integer filmId) {
		if (filmsLikes == null) {
			this.filmsLikes = new HashSet<>();
		}
		return filmsLikes.remove(filmId);
	}
}
