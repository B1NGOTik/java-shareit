package ru.practicum.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.item.dto.CommentDto;
import ru.practicum.item.dto.ItemCreateDto;
import ru.practicum.item.dto.ItemUpdateDto;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemGatewayController {
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";
    private final ItemClient client;

    @PostMapping
    public ResponseEntity<Object> createItem(@RequestBody ItemCreateDto item, @RequestHeader(USER_ID_HEADER) Long userId) {
        return client.createItem(item, userId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@RequestBody ItemUpdateDto item,
                                             @PathVariable Long itemId,
                                             @RequestHeader(USER_ID_HEADER) Long userId) {
        return client.updateItem(item, userId, itemId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> findItemById(@PathVariable Long itemId,
                                               @RequestHeader(USER_ID_HEADER) Long userId) {
        return client.findItemById(userId, itemId);
    }

    @GetMapping
    public ResponseEntity<Object> findAllItemsOfUser(@RequestHeader(USER_ID_HEADER) Long userId) {
        return client.findAllItemsOfUser(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItem(@RequestParam String text) {
        return client.searchItem(text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestBody CommentDto comment,
                                             @PathVariable Long itemId,
                                             @RequestHeader(USER_ID_HEADER) Long userId) {
        return client.addComment(comment, itemId, userId);
    }
}
