package com.paymentinitiation.exception;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException() {
        super();
    }

    public InvalidRequestException(final String message) {
        super(message);
    }
}
