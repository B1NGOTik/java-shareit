package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.ValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class UserInMemoryStorage {
    private static final HashMap<Long, User> users = new HashMap<>();

    public static boolean checkUser(Long userId) {
        return users.containsKey(userId);
    }

    public User createUser(User user) {
        emailCheck(user.getEmail());
        user.setId(getNextId());
        users.put(user.getId(), user);
        return user;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public User getUserById(Long id) {
        return users.get(id);
    }

    public User updateUser(User user, Long userId) {
        User updatingUser = users.get(userId);
        if (user.getEmail() != null) {
            emailCheck(user.getEmail());
            updatingUser.setEmail(user.getEmail());
        }
        if (user.getName() != null) {
            updatingUser.setName(user.getName());
        }
        users.put(updatingUser.getId(), updatingUser);
        return updatingUser;
    }

    public User deleteUser(Long id) {
        User user = users.get(id);
        users.remove(id);
        return user;
    }

    private Long getNextId() {
        Long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    private void emailCheck(String userEmail) {
        if (userEmail.isBlank()) {
            throw new ValidationException("Отсутствует email");
        }
        if (!userEmail.contains("@")) {
            throw new ValidationException("Неверный формат email");
        }
        for (User user : users.values()) {
            if (user.getEmail().equals(userEmail)) {
                throw new ConflictException("Такой email уже зарегистрирован");
            }
        }
    }
}
