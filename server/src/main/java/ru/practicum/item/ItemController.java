package ru.practicum.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.item.model.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";
    private final ItemService service;

    @PostMapping
    public ItemDto createItem(@RequestBody ItemCreateRequest item, @RequestHeader(USER_ID_HEADER) Long userId) {
        return service.createItem(item, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestBody ItemUpdateRequest item,
                              @PathVariable Long itemId,
                              @RequestHeader(USER_ID_HEADER) Long userId) {
        return service.updateItem(item, itemId, userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto findItemById(@PathVariable Long itemId,
                                @RequestHeader(USER_ID_HEADER) Long userId) {
        return service.getItemById(itemId);
    }

    @GetMapping
    public List<ItemDto> findAllItemsOfUser(@RequestHeader(USER_ID_HEADER) Long userId) {
        return service.getAllItemsOfUser(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItem(@RequestParam String text) {
        return service.itemSearch(text);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@RequestBody Comment comment,
                                 @PathVariable Long itemId,
                                 @RequestHeader(USER_ID_HEADER) Long userId) {
        return service.addComment(userId, itemId, comment);
    }
}
