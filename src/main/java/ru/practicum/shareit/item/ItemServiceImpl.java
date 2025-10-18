package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemInMemoryStorage storage = new ItemInMemoryStorage();

    @Override
    public Item createItem(Item item, Long userId) {
        return storage.createItem(item, userId);
    }

    @Override
    public Item updateItem(Item item, Long itemId, Long userId) {
        return storage.updateItem(item, itemId, userId);
    }

    @Override
    public Item getItemById(Long itemId, Long userId) {
        return storage.getItemById(itemId, userId);
    }

    @Override
    public List<Item> getAllItemsOfUser(Long userId) {
        return storage.getAllItemsOfUser(userId);
    }

    @Override
    public List<Item> itemSearch(String searchString) {
        return storage.itemSearch(searchString);
    }
}
