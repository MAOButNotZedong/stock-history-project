package org.example.finalproject.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User with email already exists")
public class UserWithEmailAlreadyExistsException extends RuntimeException{

    public UserWithEmailAlreadyExistsException(){}
    public UserWithEmailAlreadyExistsException(String message) {
        super(message);
    }
}

