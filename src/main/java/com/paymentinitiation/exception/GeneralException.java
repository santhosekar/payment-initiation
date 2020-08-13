package com.paymentinitiation.exception;

public class GeneralException  extends RuntimeException{
    private static final long serialVersionUID = -9079454849611061074L;

    public GeneralException() {
        super();
    }

    public GeneralException(final String message) {
        super(message);
    }
}
