package ru.practicum.shareit.request;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.ItemResponseDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemRequestService {
    private final ItemRequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public ItemRequestDto createRequest(Long userId, ItemRequest request) {
        request.setCreator(userRepository.findById(userId).get());
        request.setCreated(LocalDateTime.now());
        return ItemRequestDto.toDto(requestRepository.save(request));
    }

    public List<ItemRequestDto> getRequestsByUserId(Long userId) {
        return requestRepository.findByCreatorId(userId)
                .stream()
                .map(ItemRequestDto::toDto)
                .peek(request -> request.setItems(getResponsesForRequest(request)))
                .collect(Collectors.toList());
    }

    public ItemRequestDto getRequestById(Long requestId) {
        ItemRequest request = requestRepository.findById(requestId).get();
        ItemRequestDto requestDto = ItemRequestDto.toDto(request);
        requestDto.setItems(getResponsesForRequest(requestDto));
        return requestDto;
    }

    private List<ItemResponseDto> getResponsesForRequest(ItemRequestDto request) {
        return itemRepository.findByRequestId(request.getId())
                .stream()
                .map(ItemResponseDto::toDto)
                .collect(Collectors.toList());
    }
}
