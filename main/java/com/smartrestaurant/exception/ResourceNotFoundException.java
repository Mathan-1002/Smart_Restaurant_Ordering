package com.smartrestaurant.exception;

// ─── Resource Not Found ────────────────────────────────────────────────────────
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
