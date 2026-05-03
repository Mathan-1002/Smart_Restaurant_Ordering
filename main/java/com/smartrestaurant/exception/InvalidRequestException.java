package com.smartrestaurant.exception;

// ─── Invalid Request (business rule violations) ────────────────────────────────
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
