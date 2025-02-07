package org.example.finalproject.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.example.finalproject.constants.ExceptionConstants;
import org.example.finalproject.exception.dto.ExceptionResponseDto;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Profile("production")
@RequiredArgsConstructor
public class InternalServerErrorHandler {

    private final GlobalExceptionHandler globalExceptionHandler;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> exception(Exception e) {
        return globalExceptionHandler.defaultExceptionResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, ExceptionConstants.UNKNOWN_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionResponseDto>  nullPointerException(NullPointerException e) {
        return globalExceptionHandler.defaultExceptionResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, ExceptionConstants.NULL_POINTER);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponseDto> constraintViolationException(ConstraintViolationException e) {
        return globalExceptionHandler.defaultExceptionResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, ExceptionConstants.CANT_SAVE_STOCKS);
    }
}
