package com.naviroq.staffhub.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
