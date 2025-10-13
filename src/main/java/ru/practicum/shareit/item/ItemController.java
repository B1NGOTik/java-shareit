package ru.practicum.shareit.item;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";
    private final ItemService service = new ItemServiceImpl();

    @PostMapping
    public Item createItem(@RequestBody Item item, @RequestHeader(USER_ID_HEADER) Long userId) {
        return service.createItem(item, userId);
    }

    @PatchMapping("/{itemId}")
    public Item updateItem(@RequestBody Item item,
                           @PathVariable Long itemId,
                           @RequestHeader(USER_ID_HEADER) Long userId) {
        return service.updateItem(item, itemId, userId);
    }

    @GetMapping("/{itemId}")
    public Item findItemById(@PathVariable Long itemId,
                             @RequestHeader(USER_ID_HEADER) Long userId) {
        return service.getItemById(itemId, userId);
    }

    @GetMapping
    public List<Item> findAllItemsOfUser(@RequestHeader(USER_ID_HEADER) Long userId) {
        return service.getAllItemsOfUser(userId);
    }

    @GetMapping("/search")
    public List<Item> searchItem(@RequestParam String text) {
        return service.itemSearch(text);
    }
}
