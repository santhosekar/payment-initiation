package com.paymentinitiation.exception;

public class AmountLimitExceedException extends RuntimeException {
  private static final long serialVersionUID = -9079454849611061074L;

  public AmountLimitExceedException() {
    super();
  }

  public AmountLimitExceedException(final String message) {
    super(message);
  }
}

