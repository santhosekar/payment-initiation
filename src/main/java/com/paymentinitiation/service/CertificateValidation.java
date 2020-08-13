package com.paymentinitiation.service;

public interface CertificateValidation {

  boolean checkWhiteListedCertificate(String certificate, String publicKey);

}
