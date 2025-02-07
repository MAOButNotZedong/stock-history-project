package org.example.finalproject.exception;


import org.springframework.web.ErrorResponse;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(){}
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}

