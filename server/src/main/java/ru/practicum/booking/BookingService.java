package ru.practicum.booking;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.exception.ForbiddenException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.item.ItemRepository;
import ru.practicum.item.model.Item;
import ru.practicum.user.UserRepository;

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
                throw new ForbiddenException("Доступ к бронированию запрещен");
            }
        } else {
            throw new NotFoundException("Бронирование не найдено");
        }
        if (approved != null && approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else if (approved != null) {
            booking.setStatus(BookingStatus.REJECTED);
        } else {
            throw new ValidationException("Действие согласования не указано");
        }
        return bookingRepository.save(booking);
    }

    public Booking findBookingById(Long userId, Long bookingId) {
        return bookingRepository.findById(bookingId).get();
    }

    public List<Booking> findBookingsByBookerId(Long bookerId) {
        return bookingRepository.findByBookerId(bookerId);
    }

    public List<Booking> findAllOwnerBookings(Long userId) {
        if(userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }
        return bookingRepository.findByItemOwnerIdOrderByStartDesc(userId);
    }

    private void validateBooking(Long userId, BookingCreateRequest booking) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }
        if (booking.getStart().isBefore(LocalDateTime.now()) || booking.getStart() == null || booking.getStart().equals(booking.getEnd())) {
            throw new RuntimeException("Некорректная дата начала бронирования");
        }
        if (booking.getEnd().isBefore(LocalDateTime.now()) || booking.getEnd() == null || booking.getEnd().equals(booking.getStart())) {
            throw new RuntimeException("Некорректная дата окончания бронирования");
        }
        Item item;
        if (itemRepository.findById(booking.getItemId()).isPresent()) {
            item = itemRepository.findById(booking.getItemId()).get();
        } else {
            throw new NotFoundException("Предмет не найден");
        }
        if (!item.getAvailable()) {
            throw new ValidationException("Предмет недоступен для бронирования");
        }
    }
}
