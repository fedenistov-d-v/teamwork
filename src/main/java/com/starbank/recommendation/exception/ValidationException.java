package com.starbank.recommendation.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends AppException {

    public ValidationException(String message) {
        super(message, "VALIDATION_ERROR", null, HttpStatus.BAD_REQUEST);
    }

    public ValidationException(String message, String code) {
        super(message, code, null, HttpStatus.BAD_REQUEST);
    }
}

