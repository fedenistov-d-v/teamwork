package com.starbank.recommendation.configuration;

import com.starbank.recommendation.api.ApiErrorResponse;
import com.starbank.recommendation.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice(annotations = org.springframework.web.bind.annotation.RestController.class)
public class ApiExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiErrorResponse> handleAppException(AppException ex) {
        log.warn("API business error: code={}, message={}", ex.getCode(), ex.getMessage());

        ApiErrorResponse body = ApiErrorResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .code(ex.getCode())
                .redirectUrl(ex.getRedirectUrl())
                .data(null)
                .build();

        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("API illegal argument: {}", ex.getMessage());

        ApiErrorResponse body = ApiErrorResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .code("BAD_REQUEST")
                .redirectUrl(null)
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalState(IllegalStateException ex) {
        log.warn("API illegal state: {}", ex.getMessage());

        ApiErrorResponse body = ApiErrorResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .code("ILLEGAL_STATE")
                .redirectUrl(null)
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleOther(Exception ex) {
        log.error("Unexpected API error", ex);

        ApiErrorResponse body = ApiErrorResponse.builder()
                .success(false)
                .message("Внутренняя ошибка сервера")
                .code("INTERNAL_ERROR")
                .redirectUrl(null)
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}

