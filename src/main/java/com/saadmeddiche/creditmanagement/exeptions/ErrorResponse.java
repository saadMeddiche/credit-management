package com.saadmeddiche.creditmanagement.exeptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Getter @Setter
public class ErrorResponse {

    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String message;

    private Map<String, String> errors;

    private String path;
}
