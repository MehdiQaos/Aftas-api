package dev.mehdi.aftas.exception.response;

import java.time.LocalDateTime;

public class ErrorResponse {
    private final String message;
    private final String details;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public ErrorResponse(String message, String details) {
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}