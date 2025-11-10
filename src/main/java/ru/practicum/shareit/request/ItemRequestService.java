package ru.practicum.shareit.request;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemRequestService {
    private final ItemRequestRepository requestRepository;
    private final UserRepository userRepository;

    public ItemRequestDto createRequest(Long userId, ItemRequest request) {
        request.setCreator(userRepository.findById(userId).get());
        request.setCreatedAt(LocalDateTime.now());
        return ItemRequestDto.toDto(requestRepository.save(request));
    }
}
