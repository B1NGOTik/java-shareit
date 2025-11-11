package ru.practicum.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.dto.UpdateUserDto;
import ru.practicum.user.dto.UserDto;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserGatewayController {

    private final UserClient userClient;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable @Positive Long id) {
        return userClient.findUserById(id);
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        return userClient.findAllUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addUser(@RequestBody @Valid UserDto user) {
        return userClient.createUser(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable @Positive Long id, @RequestBody @Valid UpdateUserDto user) {
        return userClient.updateUser(user, id);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable @Positive Long id) {
        userClient.removeUser(id);
    }
}