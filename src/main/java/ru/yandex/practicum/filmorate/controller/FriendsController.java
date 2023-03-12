package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.projection.InteractionServiceWithFriendInterface;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class FriendsController {

    private final InteractionServiceWithFriendInterface userService;

    @GetMapping("/{id}/friends")
    public ResponseEntity<List<User>> bringBackAllFriends(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.returnAllFriends(id));
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<List<User>> bringBackCommonFriends(@PathVariable Integer id,
                                                             @PathVariable Integer otherId) {
        return ResponseEntity.ok(userService.returnCommonFriends(id, otherId));
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<User> addToFriends(@PathVariable Integer id,
                                             @PathVariable Integer friendId) {
        return ResponseEntity.ok(userService.addToFriend(id, friendId));
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<User> removeFriend(@PathVariable Integer id,
                                             @PathVariable Integer friendId) {
        return ResponseEntity.ok(userService.removeFriend(id, friendId));
    }
}
