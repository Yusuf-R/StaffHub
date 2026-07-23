package com.naviroq.staffhub.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
