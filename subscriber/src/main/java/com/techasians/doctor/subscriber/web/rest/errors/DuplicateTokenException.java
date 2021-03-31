package com.techasians.doctor.subscriber.web.rest.errors;

public class DuplicateTokenException extends RuntimeException {
    public DuplicateTokenException(String message) {
        super(message);
    }
}
