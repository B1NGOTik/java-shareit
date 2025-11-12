package ru.practicum.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";
    private final BookingService service;

    @PostMapping
    public Booking createBooking(@RequestHeader(USER_ID_HEADER) Long userId, @RequestBody BookingCreateRequest booking) {
        return service.createBooking(userId, booking);
    }

    @PatchMapping("/{bookingId}")
    public Booking approveBooking(@RequestHeader(USER_ID_HEADER) Long userId,
                                  @RequestParam("approved") Boolean approved,
                                  @PathVariable Long bookingId) {
        return service.approveBooking(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public Booking findBookingById(@RequestHeader(USER_ID_HEADER) Long userId,
                                   @PathVariable Long bookingId) {
        return service.findBookingById(userId, bookingId);
    }

    @GetMapping
    public List<Booking> findBookingsByBookerId(@RequestHeader(USER_ID_HEADER) Long userId) {
        return service.findBookingsByBookerId(userId);
    }

    @GetMapping("/owner")
    public List<Booking> findAllOwnersBookings(@RequestHeader(USER_ID_HEADER) Long userId) {
        return service.findAllOwnerBookings(userId);
    }

}
