package com.starbank.recommendation.exception;

public class RuleExecutionFailException extends RuntimeException {
    public RuleExecutionFailException
            (String message) {
        super(message);
    }
}
