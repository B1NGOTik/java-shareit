package ru.practicum.request.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestDto {
    Long id;
    String description;
    Long creatorId;
    LocalDateTime created;
}
