package com.example.hotel_management.exceptions;

public class InvalidBookingStateAndDateException extends RuntimeException{
    public InvalidBookingStateAndDateException(String message) {
        super(message);
    }

    public InvalidBookingStateAndDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
