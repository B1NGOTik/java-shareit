package ru.practicum.shareit.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class  UserController {
    private final UserService service = new UserService();

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
        return service.getUserById(userId);
    }

    @PatchMapping("/{userId}")
    public User updateUser(@RequestBody User user, @PathVariable Long userId) {
        return service.updateUser(user, userId);
    }

    @DeleteMapping("/{userId}")
    public User removeUser(@PathVariable Long userId) {
        return service.deleteUser(userId);
    }
}
