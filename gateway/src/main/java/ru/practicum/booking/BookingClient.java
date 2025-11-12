package ru.practicum.booking;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.booking.dto.BookingDto;
import ru.practicum.client.BaseClient;
import ru.practicum.exception.ValidationException;

import java.time.LocalDateTime;

@Service
public class BookingClient extends BaseClient {
    public static final String API_PREFIX = "/bookings";

    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> createBooking(BookingDto booking, Long userId) {
        validateBooking(booking);
        return post("", userId, booking);
    }

    public ResponseEntity<Object> approveBooking(Long userId, Boolean approved, Long bookingId) {
        if (approved.describeConstable().isEmpty()) {
            throw new ValidationException("Действие согласования не указано");
        }
        return patch("/" + bookingId + "?approved=" + approved, userId, approved);
    }

    public ResponseEntity<Object> findBookingById(Long userId, Long bookingId) {
        return get("/" + bookingId, userId);
    }

    public ResponseEntity<Object> findBookingsByBookerId(Long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> findAllOwnersBookings(Long userId) {
        return get("/owner", userId);
    }

    private void validateBooking(BookingDto booking) {
        if (booking.getStart().isBefore(LocalDateTime.now()) || booking.getStart() == null || booking.getStart().equals(booking.getEnd())) {
            throw new RuntimeException("Некорректная дата начала бронирования");
        }
        if (booking.getEnd().isBefore(LocalDateTime.now()) || booking.getEnd() == null || booking.getEnd().equals(booking.getStart())) {
            throw new RuntimeException("Некорректная дата окончания бронирования");
        }
    }
}
