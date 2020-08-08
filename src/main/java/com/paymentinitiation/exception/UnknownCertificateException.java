package com.paymentinitiation.exception;

public class UnknownCertificateException extends RuntimeException {
  private static final long serialVersionUID = -9079454849611061074L;

  public UnknownCertificateException() {
    super();
  }

  public UnknownCertificateException(final String message) {
    super(message);
  }
}

