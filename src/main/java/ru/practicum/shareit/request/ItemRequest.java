package ru.practicum.shareit.request;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.model.ItemResponseDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "requests")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Data
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    User creator;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "created")
    LocalDateTime createdAt;
}
