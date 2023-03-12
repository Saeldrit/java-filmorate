package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.memory_repository.UserRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;

public class UserServiceTest {

    private final UserService service =
            new UserService(new UserRepository());

    @BeforeEach
    void beforeEach() {
        service.addNewUser(buildUser());
    }

    private User buildUser() {
        return User.builder()
                .login("Saeldrit")
                .email("Pavlo@gmail.com")
                .name("Alex")
                .birthday(LocalDate.of(1993, 11, 20))
                .build();
    }

    @AfterEach
    void afterEach() {
        service.cleaner();
    }

    @DisplayName("Создать пользователя без id и вернуть объект с id")
    @Test
    void shouldCreateNewUser_whenCallMethod_thenReturnThisMovie() {
        User userWithoutId = buildUser();

        User userWithId = service.addNewUser(userWithoutId);
        assertNotNull(userWithId.getId());
    }

    @DisplayName("Не создавать нового пользователя, если такой есть")
    @Test
    void shouldNotCreateNewUser_ifThereIsOne() {
        User film = buildUser();
        service.addNewUser(film);

        assertEquals(1, service.returnAllUsers().size());
    }

    @DisplayName("Вернуть пользователя, которого мы создали")
    @Test
    void shouldBringBackTheMovieWeCreated() {
        User user = buildUser();

        assertEquals(user, service.findUserById(1));
    }

    @DisplayName("Вернуть пользователя по id 1")
    @Test
    void shouldReturnFilm_whenItIsFoundById() {
        assertNotNull(service.findUserById(1));
    }

    @DisplayName("EntityNotFound если пользователь не найден")
    @Test
    void shouldThrowAnError_whenTheMovieIsNotFound_ifTheIdDoesNotExist() {
        int userId = anyInt();
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class,
                        () -> service.findUserById(userId));

        assertEquals("User not found by id " + userId,
                exception.getMessage());
    }

    @DisplayName("Обновить пользователя если значения полей поменялись")
    @Test
    void shouldUpdateMovie_whenFieldsChanged() {
        User user = service.findUserById(1);

        User filmForUpdate = User.builder()
                .id(user.getId())
                .login("Do")
                .email(user.getEmail())
                .name("Ivan")
                .birthday(user.getBirthday())
                .build();

        User userAfterUpdate = service.updateUser(filmForUpdate);

        assertAll(() -> assertNotEquals(user, userAfterUpdate),
                () -> assertNotEquals(user.getName(), userAfterUpdate.getName()),
                () -> assertNotEquals(user.getLogin(), userAfterUpdate.getLogin()),
                () -> assertEquals(1, service.returnAllUsers().size()));
    }

    @DisplayName("EntityNorFound при обновлении")
    @Test
    void shouldThrowNAnError_whenUpdateMovieIsNotFound() {
        User user = service.findUserById(1);

        User userForUpdate = User.builder()
                .id(user.getId() + 1)
                .login(user.getLogin())
                .email(user.getEmail())
                .name("Ivan")
                .birthday(user.getBirthday())
                .build();

        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class,
                        () -> service.updateUser(userForUpdate));

        assertEquals("User not found by id "
                        + userForUpdate.getId(),
                exception.getMessage());
    }

    @DisplayName("Вернуть пользователя, который мы удалили")
    @Test
    void shouldReturnMovieThatWasDeleted() {
        User userAfterRemoving = service.removeUser(1);

        assertNotNull(userAfterRemoving);
        assertTrue(service.returnAllUsers().isEmpty());
    }
}
