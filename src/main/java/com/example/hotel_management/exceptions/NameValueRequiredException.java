package com.example.hotel_management.exceptions;

public class NameValueRequiredException extends RuntimeException {
    public NameValueRequiredException(String message) {
        super(message);
    }

    public NameValueRequiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
