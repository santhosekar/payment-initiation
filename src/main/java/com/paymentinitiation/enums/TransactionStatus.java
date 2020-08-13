package com.paymentinitiation.enums;

public enum TransactionStatus {

  ACCEPTED("Accepted"), REJECTED("Rejected");

  private final String status;

  TransactionStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}
