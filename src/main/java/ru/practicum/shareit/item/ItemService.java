package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    public Item createItem(Item item, Long userId);

    public Item updateItem(Item item, Long itemId, Long userId);

    public Item getItemById(Long itemId, Long userId);

    public List<Item> getAllItemsOfUser(Long userId);

    public List<Item> itemSearch(String searchString);
}
