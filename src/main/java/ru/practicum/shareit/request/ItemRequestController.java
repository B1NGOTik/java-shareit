package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";
    private final ItemRequestService service;

    @PostMapping
    public ItemRequestDto createRequest(@RequestParam(USER_ID_HEADER) Long userId, @RequestBody ItemRequest request){
        return service.createRequest(userId, request);
    }
}
