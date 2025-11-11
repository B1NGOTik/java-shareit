package ru.practicum.booking.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.booking.model.BookingStatus;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDto {
    Long itemId;
    LocalDateTime start;
    LocalDateTime end;
}

