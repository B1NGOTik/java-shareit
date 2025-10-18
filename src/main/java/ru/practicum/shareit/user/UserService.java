package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserInMemoryStorage storage = new UserInMemoryStorage();

    public User createUser(User user) {
        return storage.createUser(user);
    }

    public List<User> getAllUsers() {
        return storage.getAllUsers();
    }

    public User getUserById(Long id) {
        return storage.getUserById(id);
    }

    public User updateUser(User user, Long userId) {
        return storage.updateUser(user, userId);
    }

    public User deleteUser(Long id) {
        return storage.deleteUser(id);
    }
}
