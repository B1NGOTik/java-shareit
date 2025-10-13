package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults
public class Item {
    Long id;
    String name;
    String description;
    Long ownerId;
    Boolean available;
}
