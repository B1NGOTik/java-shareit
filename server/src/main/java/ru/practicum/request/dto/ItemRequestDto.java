package ru.practicum.request.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.item.model.ItemResponseDto;
import ru.practicum.request.ItemRequest;

import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemRequestDto {
    Long id;
    String description;
    Long creatorId;
    LocalDateTime created;
    List<ItemResponseDto> items;

    public static ItemRequestDto toDto(ItemRequest request) {
        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(request.getId());
        dto.setDescription(request.getDescription());
        dto.setCreatorId(request.getCreator().getId());
        dto.setCreated(request.getCreated());
        return dto;
    }
}
