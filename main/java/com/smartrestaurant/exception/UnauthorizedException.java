package com.smartrestaurant.exception;

// ─── Unauthorized (login failure) ─────────────────────────────────────────────
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
