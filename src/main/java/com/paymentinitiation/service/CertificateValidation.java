package com.paymentinitiation.service;

import java.io.IOException;

import com.paymentinitiation.model.PaymentDetails;

public interface CertificateValidation {

  boolean CheckValidCertificate(String certificate, String publicKey, PaymentDetails paymentDetails,
      String paymentId) throws IOException;

}
