package ru.practicum.item;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.client.BaseClient;
import ru.practicum.item.dto.CommentDto;
import ru.practicum.item.dto.ItemCreateDto;
import ru.practicum.item.dto.ItemUpdateDto;

@Service
public class ItemClient extends BaseClient {
    public static final String API_PREFIX = "/items";

    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> createItem(ItemCreateDto item, Long userId) {
        validateItem(item);
        return post("", userId, item);
    }

    public ResponseEntity<Object> updateItem(ItemUpdateDto item, Long userId, Long itemId) {
        return patch("/" + itemId, userId, item);
    }

    public ResponseEntity<Object> findItemById(Long userId, Long itemId) {
        return get("/" + itemId, userId);
    }

    public ResponseEntity<Object> findAllItemsOfUser(Long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> searchItem(String text) {
        return get("/search?text=" + text);
    }

    public ResponseEntity<Object> addComment(CommentDto comment, Long itemId, Long userId) {
        return post("/" + itemId + "/comment", userId, comment);
    }

    private void validateItem(ItemCreateDto item) {
        if (!item.hasAvailable()) {
            throw new ValidationException("Поле доступности должно быть заполнено");
        }
        if (!item.hasName()) {
            throw new ValidationException("Поле с названием предмета должно быть заполнено");
        }
        if (!item.hasDescription()) {
            throw new ValidationException("Поле с описанием предмета должно быть заполнено");
        }
    }
}

