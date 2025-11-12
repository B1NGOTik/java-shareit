package ru.practicum.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.RequestDto;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/requests")
public class ItemRequestGatewayController {
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";
    private final ItemRequestClient client;

    @PostMapping
    public ResponseEntity<Object> createRequest(@RequestHeader(USER_ID_HEADER) Long userId, @RequestBody RequestDto request) {
        return client.createRequest(userId, request);
    }

    @GetMapping
    public ResponseEntity<Object> findRequestsByCreatorId(@RequestHeader(USER_ID_HEADER) Long userId) {
        return client.findRequestsByCreatorId(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> findRequestById(@PathVariable Long requestId) {
        return client.findRequestById(requestId);
    }
}
