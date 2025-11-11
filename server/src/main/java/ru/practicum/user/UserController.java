package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.model.User;
import ru.practicum.user.model.UserUpdateRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserService service;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    @GetMapping
    public List<User> findAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User findUserById(@PathVariable Long userId) {
        return service.getUserById(userId).get();
    }

    @PatchMapping("/{userId}")
    public User updateUser(@RequestBody UserUpdateRequest user, @PathVariable Long userId) {
        return service.updateUser(user, userId);
    }

    @DeleteMapping("/{userId}")
    public void removeUser(@PathVariable Long userId) {
        service.deleteUser(userId);
    }
}
