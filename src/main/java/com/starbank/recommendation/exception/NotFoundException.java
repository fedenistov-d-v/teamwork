package com.starbank.recommendation.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AppException {

  public NotFoundException(String message) {
    super(message, "NOT_FOUND", null, HttpStatus.NOT_FOUND);
  }
}
