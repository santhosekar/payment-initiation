package com.paymentinitiation.implementation;

import static com.paymentinitiation.constant.PaymentInitiationConstant.RSA;
import static com.paymentinitiation.constant.PaymentInitiationConstant.SHA_1_WITH_RSA;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.paymentinitiation.enums.ErrorReasonCode;
import com.paymentinitiation.exception.GeneralException;
import com.paymentinitiation.service.CertificateValidation;

@Component
public class CertificateValidationImpl implements CertificateValidation {

  Logger logger = LoggerFactory.getLogger(CertificateValidationImpl.class);

  List<byte[]> certificateList;

  @Override
  public boolean checkWhiteListedCertificate(String certificate, String publicKey)
      throws IOException {
    logger.debug("Entering method name is : {}", "checkWhiteListedCertificate");
    ObjectInputStream objectInputStream = null;
    try {
      byte[] bytePubKey = Base64.getDecoder().decode(certificate);

      KeyFactory factory = KeyFactory.getInstance(RSA);
      PublicKey key = factory.generatePublic(new X509EncodedKeySpec(bytePubKey));
      byte[] byteSignature = Base64.getDecoder().decode(publicKey);
      objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteSignature));
      certificateList = (List<byte[]>) objectInputStream.readObject();
      objectInputStream.close();
      return (verifySignature(certificateList.get(0), certificateList.get(1), key));
    } catch (Exception e) {
      logger.info("checkWhiteListedCertificate getting exception : {}",
          ErrorReasonCode.GENERAL_ERROR.getReasonCode());
      if (null != objectInputStream) {
        objectInputStream.close();
      }
      throw new GeneralException(e.getMessage());
    }

  }

  private boolean verifySignature(byte[] data, byte[] signature, PublicKey publicKey)
      throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    logger.debug("Entering method name is : {}", "verifySignature");
    Signature receivedSignature = Signature.getInstance(SHA_1_WITH_RSA);
    receivedSignature.initVerify(publicKey);
    receivedSignature.update(data);
    logger.debug("Exiting method name is : {}", "verifySignature");
    return receivedSignature.verify(signature);
  }


}
