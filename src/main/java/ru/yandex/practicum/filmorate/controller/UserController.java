package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.projection.UserServiceInterface;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserServiceInterface userService;

    @GetMapping
    public ResponseEntity<List<User>> bringBackAllUsers() {
        return ResponseEntity.ok(userService.returnAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@Valid @PathVariable Integer id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PostMapping
    public ResponseEntity<User> addNewUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.addNewUser(user));
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> removeUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.removeUser(id));
    }
}
