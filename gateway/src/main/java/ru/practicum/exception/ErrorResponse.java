package ru.practicum.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private String error;

    public ErrorResponse(String message) {
        error = message;
    }
}