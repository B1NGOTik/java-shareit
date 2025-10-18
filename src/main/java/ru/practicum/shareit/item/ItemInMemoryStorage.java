package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserInMemoryStorage;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ItemInMemoryStorage {
    private final HashMap<Long, Item> items = new HashMap<>();

    public Item createItem(Item item, Long userId) {
        if (!UserInMemoryStorage.checkUser(userId)) {
            throw new NotFoundException("Пользователя с таким ID не существует");
        }
        checkItem(item);
        item.setOwnerId(userId);
        item.setId(getNextId());
        items.put(item.getId(), item);
        return item;
    }

    public List<Item> itemSearch(String searchString) {
        if (searchString == null || searchString.trim().isEmpty()) {
            return Collections.emptyList();
        }

        String lowerSearch = searchString.toLowerCase();
        return items.values()
                .stream()
                .filter(item ->
                        ((item.getName() != null && item.getName().toLowerCase().contains(lowerSearch)) ||
                                (item.getDescription() != null && item.getDescription().toLowerCase().contains(lowerSearch))) &&
                                (item.getAvailable()))

                .collect(Collectors.toList());
    }

    public List<Item> getAllItemsOfUser(Long userId) {
        return items.values()
                .stream()
                .filter(item -> item.getOwnerId().equals(userId))
                .collect(Collectors.toList());
    }

    public Item getItemById(Long itemId, Long userId) {
        return items.get(itemId);
    }

    public Item updateItem(Item item, Long itemId, Long userId) {
        Item updatedItem = items.get(itemId);
        if (!userId.equals(updatedItem.getOwnerId())) {
            throw new ForbiddenException("У пользователя нет доступа к изменению этого предмета");
        }
        if (item.getAvailable() != null) {
            updatedItem.setAvailable(item.getAvailable());
        }
        if (item.getName() != null) {
            updatedItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            updatedItem.setDescription(item.getDescription());
        }
        items.put(itemId, updatedItem);
        return updatedItem;
    }

    private Long getNextId() {
        long currentId = items.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentId;
    }

    private void checkItem(Item item) {
        if (item.getAvailable() == null) {
            throw new ValidationException("Поле доступности должно быть заполнено");
        }
        if (item.getName().isBlank()) {
            throw new ValidationException("Поле с названием предмета должно быть заполнено");
        }
        if (item.getDescription().isBlank()) {
            throw new ValidationException("Поле с описанием предмета должно быть заполнено");
        }
    }

}
