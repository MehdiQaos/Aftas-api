package dev.mehdi.aftas.exception;

import java.util.Map;

public class InvalidRequestException extends RuntimeException {
    private Map<String, String> errors;

    public InvalidRequestException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public static InvalidRequestException of(String key, String message) {
        return new InvalidRequestException("Invalid request", Map.of(key, message));
    }
}