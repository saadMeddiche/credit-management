package com.saadmeddiche.creditmanagement.exeptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) throws MethodArgumentNotValidException {
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .status(400)
                .error("Bad Request")
                .message("Validation error")
                .timestamp(LocalDateTime.now())
                .errors(exception.getBindingResult().getFieldErrors().stream().collect(
                        java.util.stream.Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)))
                .build());
    }
}
