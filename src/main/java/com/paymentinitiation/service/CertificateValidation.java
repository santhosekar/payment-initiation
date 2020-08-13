package com.paymentinitiation.service;

import java.io.IOException;

public interface CertificateValidation {

  boolean checkWhiteListedCertificate(String certificate, String publicKey) throws IOException;

}
