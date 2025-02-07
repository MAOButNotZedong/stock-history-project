package org.example.finalproject.exception;


public class TickerNotFoundException extends RuntimeException {
    public TickerNotFoundException(String message) {
        super(message);
    }
}
