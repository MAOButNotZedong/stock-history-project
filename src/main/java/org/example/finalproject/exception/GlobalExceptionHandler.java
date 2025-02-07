package org.example.finalproject.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.apache.commons.lang3.StringUtils;
import org.example.finalproject.constants.ExceptionConstants;
import org.example.finalproject.exception.dto.ExceptionResponseDto;
import org.example.finalproject.exception.dto.MultiExceptionResponseDto;
import org.example.finalproject.exception.dto.ValidationExceptionMessage;
import org.example.finalproject.model.entity.User;
import org.hibernate.engine.jdbc.env.spi.SQLStateType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<ExceptionResponseDto> defaultExceptionResponse(Throwable e, HttpStatus status, String message) {
        ExceptionResponseDto responseDto = ExceptionResponseDto.builder()
                .status(status.getReasonPhrase())
                .exceptionType(e.getClass().toString())
                .message(message)
                .build();
        return ResponseEntity.status(status).body(responseDto);
    }

    @ExceptionHandler(TickerNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> tickerNotFoundException(TickerNotFoundException e) {
        return defaultExceptionResponse(e,HttpStatus.NOT_FOUND, ExceptionConstants.TICKER_NOT_FOUND + ": " + e.getMessage());
    }

    @ExceptionHandler(UserWithEmailAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponseDto>  userWithEmailAlreadyExists(UserWithEmailAlreadyExistsException e) {
        return defaultExceptionResponse(e, HttpStatus.CONFLICT, ExceptionConstants.USER_WITH_EMAIL_ALREADY_EXISTS);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponseDto> dataIntegrityViolationException(DataIntegrityViolationException e) {
        String sqlState = ((SQLException) e.getMostSpecificCause()).getSQLState();
        if (sqlState.equals(ExceptionConstants.UNIQUE_VIOLATION) && StringUtils.containsIgnoreCase(e.getMessage(), User.UNIQUE_CONSTRAINT_NAME)) {
            return defaultExceptionResponse(e, HttpStatus.CONFLICT, ExceptionConstants.USER_WITH_EMAIL_ALREADY_EXISTS);
        }
        return defaultExceptionResponse(e, HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionResponseDto>  expiredJwtException(ExpiredJwtException e) {
        return defaultExceptionResponse(e, HttpStatus.UNAUTHORIZED, ExceptionConstants.JWT_TOKEN_EXPIRED);
    }

    @ExceptionHandler({SignatureException.class, MalformedJwtException.class})
    public ResponseEntity<ExceptionResponseDto>  signatureException(Exception e) {
        return defaultExceptionResponse(e, HttpStatus.UNAUTHORIZED, ExceptionConstants.BAD_JWT_TOKEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponseDto>  badCredentialsException(BadCredentialsException e) {
        return defaultExceptionResponse(e, HttpStatus.UNAUTHORIZED, ExceptionConstants.EMAIL_OR_PASSWORD_NOT_VALID);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponseDto>  httpMessageNotReadableException(HttpMessageNotReadableException e) {
        return defaultExceptionResponse(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.TooManyRequests.class)
    public ResponseEntity<ExceptionResponseDto>  tooManyRequests(HttpClientErrorException e) {
        return defaultExceptionResponse(e, HttpStatus.TOO_MANY_REQUESTS, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MultiExceptionResponseDto>  methodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ValidationExceptionMessage> list = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            list.add(new ValidationExceptionMessage(fieldError.getField(), fieldError.getDefaultMessage()));
        });
        MultiExceptionResponseDto responseDto = MultiExceptionResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .messages(Collections.singletonList(list))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }



}
