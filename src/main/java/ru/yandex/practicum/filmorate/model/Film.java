package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.NonFinal;
import ru.yandex.practicum.filmorate.validation.ValidationDate;

import javax.persistence.Transient;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Value
@With
@Builder(toBuilder = true)
@EqualsAndHashCode(exclude = {"id", "description", "duration", "usersLikes"})
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Film {

	Integer id;

	@NotBlank
	String name;

	@Size(max = 200)
	String description;

	@Past
	@ValidationDate
	@NotNull
	LocalDate releaseDate;

	@Min(0)
	Long duration;

	@JsonIgnore
	@NonFinal
	@Transient
	Set<Integer> usersLikes;

	MPA mpa;

	Integer rate;

	@NonFinal
	List<Genre> genres;

	public int getRate() {
		return this.rate != null ?
				this.rate : 0;
	}

	public boolean addToLikesSet(Integer userId) {
		if (usersLikes == null) {
			this.usersLikes = new HashSet<>();
		}
		return usersLikes.add(userId);
	}

	public Set<Integer> getUsersLikes() {
		return this.usersLikes != null ?
				this.usersLikes :
				(this.usersLikes = new HashSet<>());
	}

	public boolean removeFromLikesSet(Integer userId) {
		if (usersLikes == null) {
			this.usersLikes = new HashSet<>();
		}
		return usersLikes.remove(userId);
	}

	public int countLikes() {
		return usersLikes != null ? usersLikes.size() : 0;
	}

	public List<Genre> getGenres() {
		return this.genres != null ?
				this.genres : Collections.emptyList();
	}

	public void setGenres(List<Genre> genres) {
		if (genres != null) {
			this.genres = genres;
		}
	}
}
