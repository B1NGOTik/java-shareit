package ru.practicum.item.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.user.model.User;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "items", schema = "public")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "description")
    String description;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    User owner;

    @Column(name = "available", nullable = false)
    Boolean available;

    @Column(name = "request_id")
    Long requestId;
}
