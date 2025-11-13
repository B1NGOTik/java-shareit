package ru.practicum.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.ItemRequestDto;

import java.util.List;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";
    private final ItemRequestService service;

    @PostMapping
    public ItemRequestDto createRequest(@RequestHeader(USER_ID_HEADER) Long userId, @RequestBody ItemRequest request) {
        return service.createRequest(userId, request);
    }

    @GetMapping
    public List<ItemRequestDto> findRequestsByCreatorId(@RequestHeader(USER_ID_HEADER) Long userId) {
        return service.getRequestsByUserId(userId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto findRequestById(@PathVariable Long requestId) {
        return service.getRequestById(requestId);
    }
}
