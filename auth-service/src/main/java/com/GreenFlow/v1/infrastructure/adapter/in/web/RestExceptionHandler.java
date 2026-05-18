package com.GreenFlow.v1.infrastructure.adapter.in.web;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.GreenFlow.v1.domain.exception.InvalidCredentialsException;
import com.GreenFlow.v1.domain.exception.UsernameAlreadyExistsException;
import com.GreenFlow.v1.domain.exception.UserNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCredentials(InvalidCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "timestamp", Instant.now().toString(),
                        "status", HttpStatus.UNAUTHORIZED.value(),
                        "error", HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                        "message", exception.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "timestamp", Instant.now().toString(),
                        "status", HttpStatus.NOT_FOUND.value(),
                        "error", HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "message", exception.getMessage()));
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameAlreadyExists(UsernameAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "timestamp", Instant.now().toString(),
                        "status", HttpStatus.CONFLICT.value(),
                        "error", HttpStatus.CONFLICT.getReasonPhrase(),
                        "message", exception.getMessage()));
    }
}