package ru.practicum.shareit.user;

import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults
public class User {
    Long id;
    String name;
    String email;
}
