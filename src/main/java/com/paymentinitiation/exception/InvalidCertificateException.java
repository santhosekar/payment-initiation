package com.paymentinitiation.exception;

public class InvalidCertificateException extends RuntimeException {
  private static final long serialVersionUID = -9079454849611061074L;

  public InvalidCertificateException() {
    super();
  }

  public InvalidCertificateException(final String message) {
    super(message);
  }
}

