package com.saadmeddiche.creditmanagement.exeptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionsRestHandler {

    // This method is used to handle validation errors that are thrown by the @Valid annotation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) throws MethodArgumentNotValidException {
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .status(400)
                .error("Bad Request")
                .message("Validation error")
                .timestamp(LocalDateTime.now())
                .errors(exception.getBindingResult().getFieldErrors().stream().collect(
                        Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)))
                .build());
    }

    // This method is used to handle validation errors that are thrown by the @Valid annotation and the target object is a list
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(HandlerMethodValidationException exception) throws MethodArgumentNotValidException {

        FieldError fieldError = (FieldError) exception.getAllErrors().get(0);

        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .status(400)
                .error("Bad Request")
                .message("Validation error")
                .timestamp(LocalDateTime.now())
                .errors(Map.of(fieldError.getField(), Objects.requireNonNull(fieldError.getDefaultMessage())))
                .build());
    }
}
