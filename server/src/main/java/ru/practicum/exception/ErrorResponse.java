package ru.practicum.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class ErrorResponse {
    private String error;

    public ErrorResponse(String message) {
        error = message;
    }
}
