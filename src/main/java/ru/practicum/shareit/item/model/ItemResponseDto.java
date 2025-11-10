package ru.practicum.shareit.item.model;

import lombok.Data;

@Data
public class ItemResponseDto {
    private Long id;
    private String name;
    private Long ownerId;

    public static ItemResponseDto toDto(Item item) {
        ItemResponseDto dto = new ItemResponseDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setOwnerId(item.getOwner().getId());
        return dto;
    }
}

