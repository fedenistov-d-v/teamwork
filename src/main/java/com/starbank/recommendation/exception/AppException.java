package com.starbank.recommendation.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {

    private final String code;
    private final String redirectUrl;
    private final HttpStatus status;

    public AppException(String message) {
        this(message, "APP_ERROR", null, HttpStatus.BAD_REQUEST);
    }

    public AppException(String message, String code) {
        this(message, code, null, HttpStatus.BAD_REQUEST);
    }

    public AppException(String message, String code, String redirectUrl) {
        this(message, code, redirectUrl, HttpStatus.BAD_REQUEST);
    }

    public AppException(String message, String code, String redirectUrl, HttpStatus status) {
        super(message);
        this.code = code;
        this.redirectUrl = redirectUrl;
        this.status = status == null ? HttpStatus.BAD_REQUEST : status;
    }
}
