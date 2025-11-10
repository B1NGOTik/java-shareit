package ru.practicum.shareit.item;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.*;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    public ItemDto createItem(ItemCreateRequest item, Long userId) {
        validateItem(item);
        Item creatingItem = new Item();
        if (userRepository.findById(userId).isPresent()) {
            creatingItem.setOwner(userRepository.findById(userId).get());
        } else {
            throw new NotFoundException("Пользователь не найден");
        }
        creatingItem.setName(item.getName());
        creatingItem.setDescription(item.getDescription());
        creatingItem.setAvailable(item.getAvailable());
        if(item.hasRequestId()) {
            creatingItem.setRequestId(item.getRequestId());
        }
        itemRepository.save(creatingItem);
        return new ItemDto(creatingItem);
    }

    public ItemDto updateItem(ItemUpdateRequest item, Long itemId, Long userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }
        Item existingItem = itemRepository.findById(itemId).get();
        if (!existingItem.getOwner().equals(userRepository.findById(userId).get())) {
            throw new ForbiddenException("Доступ к изменению предмета запрещен");
        }
        if (item.hasName()) {
            existingItem.setName(item.getName());
        }
        if (item.hasDescription()) {
            existingItem.setDescription(item.getDescription());
        }
        if (item.hasAvailable()) {
            existingItem.setAvailable(item.getAvailable());
        }
        itemRepository.save(existingItem);
        return getBookingsForItem(getCommentsForItem(existingItem));
    }

    public ItemDto getItemById(Long itemId) {
        Item item = itemRepository.findById(itemId).get();
        return getBookingsForItem(getCommentsForItem(item));
    }

    public List<ItemDto> getAllItemsOfUser(Long userId) {
        return itemRepository.findByOwnerId(userId).stream()
                .map(this::getCommentsForItem)
                .map(this::getBookingsForItem)
                .collect(Collectors.toList());
    }

    public List<ItemDto> itemSearch(String searchString) {
        if (searchString == null || searchString.isBlank()) {
            return List.of();
        }
        return itemRepository.search(searchString)
                .stream()
                .map(this::getCommentsForItem)
                .map(this::getBookingsForItem)
                .collect(Collectors.toList());
    }

    public CommentDto addComment(Long userId, Long itemId, Comment comment) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмет не найден"));
        List<Booking> userBookings = bookingRepository.findByBookerIdAndItemId(userId, itemId);

        boolean hasCompletedBooking = userBookings.stream()
                .anyMatch(booking ->
                        booking.getEnd().isBefore(LocalDateTime.now()) &&
                                booking.getStatus().equals(BookingStatus.APPROVED));

        if (!hasCompletedBooking) {
            throw new ValidationException("Нельзя оставить комментарий к предмету без завершенного бронирования");
        }

        Comment creatingComment = new Comment();
        creatingComment.setItem(item);
        creatingComment.setUser(user);
        creatingComment.setText(comment.getText());
        creatingComment.setCreated(LocalDateTime.now());

        commentRepository.save(creatingComment);
        return new CommentDto(creatingComment);
    }

    private void validateItem(ItemCreateRequest item) {
        if (!item.hasAvailable()) {
            throw new ValidationException("Поле доступности должно быть заполнено");
        }
        if (!item.hasName()) {
            throw new ValidationException("Поле с названием предмета должно быть заполнено");
        }
        if (!item.hasDescription()) {
            throw new ValidationException("Поле с описанием предмета должно быть заполнено");
        }
    }

    private ItemDto getCommentsForItem(Item item) {
        List<CommentDto> comments = commentRepository.findByItemId(item.getId())
                .stream()
                .map(CommentDto::new)
                .collect(Collectors.toList());
        ItemDto itemDto = new ItemDto(item);
        itemDto.setComments(comments);
        return itemDto;
    }

    private ItemDto getBookingsForItem(ItemDto item) {
        List<Booking> bookings = bookingRepository.findByItemId(item.getId());
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastBookingDate = bookings.stream()
                .filter(booking -> booking.getEnd().isBefore(now))
                .filter(booking -> booking.getStatus().equals(BookingStatus.APPROVED))
                .filter(booking -> booking.getEnd().isBefore(now.minusMinutes(1)))
                .map(Booking::getEnd)
                .max(LocalDateTime::compareTo)
                .orElse(null);
        LocalDateTime nextBookingDate = bookings.stream()
                .filter(booking -> booking.getStart().isAfter(now))
                .filter(booking -> booking.getStatus().equals(BookingStatus.APPROVED))
                .map(Booking::getStart)
                .min(LocalDateTime::compareTo)
                .orElse(null);
        item.setLastBooking(lastBookingDate);
        item.setNextBooking(nextBookingDate);
        return item;
    }
}
