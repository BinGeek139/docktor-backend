package com.techasians.doctor.subscriber.exception;

public class ResourceNotFoundException  extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
