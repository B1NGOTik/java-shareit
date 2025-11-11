package ru.practicum.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.booking.dto.BookingDto;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingGatewayController {
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";
    private final BookingClient client;

    @PostMapping
    public ResponseEntity<Object> createBooking(@RequestHeader(USER_ID_HEADER) Long userId, @RequestBody BookingDto booking) {
        return client.createBooking(booking, userId);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approveBooking(@RequestHeader(USER_ID_HEADER) Long userId,
                                  @RequestParam("approved") Boolean approved,
                                  @PathVariable Long bookingId) {
        return client.approveBooking(userId, approved, bookingId);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> findBookingById(@RequestHeader(USER_ID_HEADER) Long userId,
                                   @PathVariable Long bookingId) {
        return client.findBookingById(userId, bookingId);
    }

    @GetMapping
    public ResponseEntity<Object> findBookingsByBookerId(@RequestHeader(USER_ID_HEADER) Long userId) {
        return client.findBookingsByBookerId(userId);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> findAllOwnersBookings(@RequestHeader(USER_ID_HEADER) Long userId) {
        return client.findAllOwnersBookings(userId);
    }
}
