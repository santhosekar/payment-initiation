package com.paymentinitiation.implementation;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.paymentinitiation.controller.PaymentInitiationController;
import com.paymentinitiation.service.CertificateValidation;

@Component
public class CertificateValidationImpl implements CertificateValidation {
  Logger logger = LoggerFactory.getLogger(PaymentInitiationController.class);

  List<byte[]> certificateList;

  @Override
  public boolean checkWhiteListedCertificate(String certificate, String publicKey)
      throws Exception {
    logger.debug("Entering checkWhiteListedCertificate");
    try {
      byte[] byte_pubKey = Base64.getDecoder().decode(certificate);
      KeyFactory factory = KeyFactory.getInstance("RSA");
      PublicKey key = factory.generatePublic(new X509EncodedKeySpec(byte_pubKey));
      byte[] byte_Signature = Base64.getDecoder().decode(publicKey);
      ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(byte_Signature));
      certificateList = (List<byte[]>) in.readObject();
      in.close();
      return (verifySignature(certificateList.get(0), certificateList.get(1), key));
    } catch (Exception e) {
      logger.info("Issue with certificate");
      throw new Exception(e);
    }

  }

  private boolean verifySignature(byte[] data, byte[] signature, PublicKey publicKey)
      throws Exception {
    logger.debug("Entering verifySignature");
    Signature sig = Signature.getInstance("SHA1withRSA");
    sig.initVerify(publicKey);
    sig.update(data);
    logger.debug("<---verifySignature----->");
    return sig.verify(signature);
  }


}
