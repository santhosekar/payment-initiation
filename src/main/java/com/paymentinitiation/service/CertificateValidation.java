package com.paymentinitiation.service;

import java.io.IOException;

@FunctionalInterface
public interface CertificateValidation {

     boolean checkWhiteListedCertificate(String certificate,String publicKey) throws Exception;
}
