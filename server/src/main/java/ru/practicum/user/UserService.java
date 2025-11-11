package ru.practicum.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.exception.ValidationException;
import ru.practicum.user.model.User;
import ru.practicum.user.model.UserUpdateRequest;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository repository;

    public User createUser(User user) {
        validateUser(user);
        return repository.save(user);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return repository.findById(id);
    }

    public User updateUser(UserUpdateRequest user, Long userId) {
        User existingUser = repository.findById(userId).get();
        if (user.hasName()) {
            existingUser.setName(user.getName());
        }
        if (user.hasEmail()) {
            existingUser.setEmail(user.getEmail());
        }
        return repository.save(existingUser);
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    private void validateUser(User user) {
        if (!user.getEmail().contains("@")) {
            throw new ValidationException("Неверный формат email");
        }
    }
}
