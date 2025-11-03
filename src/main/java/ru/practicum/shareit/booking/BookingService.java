package ru.practicum.shareit.booking;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public Booking createBooking(Long userId, BookingCreateRequest booking) {
        validateBooking(userId, booking);
        Booking creatingBooking = new Booking();
        creatingBooking.setBooker(userRepository.findById(userId).get());
        creatingBooking.setItem(itemRepository.findById(booking.getItemId()).get());
        creatingBooking.setStart(booking.getStart());
        creatingBooking.setEnd(booking.getEnd());
        creatingBooking.setStatus(BookingStatus.WAITING);
        bookingRepository.save(creatingBooking);
        return creatingBooking;
    }

    public Booking approveBooking(Long userId, Long bookingId, Boolean approved) {
        Booking booking;
        if (bookingRepository.findById(bookingId).isPresent()) {
            booking = bookingRepository.findById(bookingId).get();
            Long ownerId = bookingRepository.findById(bookingId).get().getItem().getOwner().getId();
            if (!userId.equals(ownerId)) {
                throw new ForbiddenException("У пользователя нет доступа к этому бронированию");
            }
        } else {
            throw new NotFoundException("Бронирования с таким ID не существует");
        }
        if (approved != null && approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else if (approved != null) {
            booking.setStatus(BookingStatus.REJECTED);
        } else {
            throw new ValidationException("Не выбрано действие согласования");
        }
        return bookingRepository.save(booking);
    }

    public Booking findBookingById(Long userId, Long bookingId) {
        return bookingRepository.findById(bookingId).get();
    }

    public List<Booking> findBookingsByBookerId(Long bookerId) {
        return bookingRepository.findByBookerId(bookerId);
    }

    private void validateBooking(Long userId, BookingCreateRequest booking) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("Пользователя с таким id не существует");
        }
        if (booking.getStart().isBefore(LocalDateTime.now()) || booking.getStart() == null || booking.getStart().equals(booking.getEnd())) {
            throw new RuntimeException("Неверная дата начала аренды");
        }
        if (booking.getEnd().isBefore(LocalDateTime.now()) || booking.getEnd() == null || booking.getEnd().equals(booking.getStart())) {
            throw new RuntimeException("Неверная дата конца аренды");
        }
        Item item;
        if (itemRepository.findById(booking.getItemId()).isPresent()) {
            item = itemRepository.findById(booking.getItemId()).get();
        } else {
            throw new NotFoundException("Предмета с таким Id не существует");
        }
        if (!item.getAvailable()) {
            throw new ValidationException("Предмет недоступен для бронирования");
        }
    }
}
