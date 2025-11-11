package ru.practicum.booking.dto;

import lombok.Data;
import ru.practicum.booking.Booking;
import ru.practicum.booking.BookingStatus;

import java.time.LocalDateTime;

@Data
public class BookingDto {
    private Long id;
    private Long itemId;
    private Long bookerId;
    private LocalDateTime start;
    private LocalDateTime end;
    private BookingStatus status;

    public BookingDto(Booking booking) {
        id = booking.getId();
        itemId = booking.getItem().getId();
        bookerId = booking.getBooker().getId();
        start = booking.getStart();
        end = booking.getEnd();
        status = booking.getStatus();
    }
}
