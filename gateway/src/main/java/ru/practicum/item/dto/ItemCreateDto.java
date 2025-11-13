package ru.practicum.item.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ItemCreateDto {
    String name;
    String description;
    Boolean available;
    Long requestId;

    public boolean hasName() {
        return !(name == null || name.isBlank());
    }

    public boolean hasDescription() {
        return !(description == null || description.isBlank());
    }

    public boolean hasAvailable() {
        return available != null;
    }

    public boolean hasRequestId() {
        return requestId != null && requestId != 0;
    }
}
