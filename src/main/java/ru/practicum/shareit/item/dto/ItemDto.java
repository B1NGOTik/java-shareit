package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.model.Item;

@Data
@FieldDefaults
public class ItemDto {
    String name;
    String description;
    Long numberOfUses;

    public static ItemDto mapFromItemToDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        return itemDto;
    }

    public static Item mapFromDtoToItem(ItemDto itemDto) {
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        return item;
    }
}
