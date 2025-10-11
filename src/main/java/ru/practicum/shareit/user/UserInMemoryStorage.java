package ru.practicum.shareit.user;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.ValidationEcxeption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class UserInMemoryStorage {
    private final HashMap<Long, User> users = new HashMap<>();

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
        if(user.getEmail()!=null){
            emailCheck(user.getEmail());
            updatingUser.setEmail(user.getEmail());
        }
        if(user.getName()!=null) {
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
        if(userEmail.isBlank()){
            throw new ValidationEcxeption("Отсутствует email");
        }
        if(!userEmail.contains("@")) {
            throw new ValidationEcxeption("Неверный формат email");
        }
        for(User user : users.values()) {
            if(user.getEmail().equals(userEmail)) {
                throw new ConflictException("Такой email уже зарегистрирован");
            }
        }
    }
}
