package com.saadmeddiche.creditmanagement.exeptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFound extends RuntimeException {
    public RecordNotFound(String message) {
        super(message);
    }
}
