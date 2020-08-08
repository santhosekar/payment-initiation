package com.paymentinitiation.factory;

import com.paymentinitiation.model.PaymentDetails;

public final class PaymentInitiationFactory {

  public static PaymentDetails getPaymentValue() {
    PaymentDetails paymentDetails = new PaymentDetails();
    paymentDetails.setDebtorIBAN("NL02RABO7134384551");
    paymentDetails.setCreditorIBAN("NL94ABNA1008270121");
    paymentDetails.setCurrency("EUR");
    paymentDetails.setAmount("1");
    return paymentDetails;
  }

  public static PaymentDetails getPaymentValueError() {
    PaymentDetails paymentDetails = new PaymentDetails();
    paymentDetails.setDebtorIBAN("NL02RABO7134384551");
    paymentDetails.setCreditorIBAN("NL94ABNA1008270121");
    paymentDetails.setCurrency("EURTYYT");
    paymentDetails.setAmount("1");
    return paymentDetails;
  }

  public static PaymentDetails getPaymentCorrect() {
    PaymentDetails paymentDetails = new PaymentDetails();
    paymentDetails.setDebtorIBAN("NL02RABO0000001555");
    paymentDetails.setCreditorIBAN("NL94ABNA1008270121");
    paymentDetails.setCurrency("EUR");
    paymentDetails.setAmount("1");
    return paymentDetails;
  }
}