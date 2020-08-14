package com.paymentinitiation.implementation;

import static com.paymentinitiation.constant.PaymentInitiationConstant.CN_SANDBOX_TPP;
import static com.paymentinitiation.constant.PaymentInitiationConstant.SHA_1_WITH_RSA;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.paymentinitiation.enums.ErrorReasonCode;
import com.paymentinitiation.exception.GeneralException;
import com.paymentinitiation.exception.UnknownCertificateException;
import com.paymentinitiation.model.PaymentDetails;
import com.paymentinitiation.service.CertificateValidation;

@Component
public class CertificateValidationImpl implements CertificateValidation {

  Logger logger = LoggerFactory.getLogger(CertificateValidationImpl.class);

  List<byte[]> certificateList;
  CertificateFactory certificateFactory;

  @Override
  public boolean CheckValidCertificate(String certificate, String signature,
      PaymentDetails paymentDetails, String paymentId) throws IOException {
    logger.debug("Entering method name is : {}", "CheckValidCertificate");
    ObjectInputStream objectInputStream = null;
    try {
      byte[] decoded = DatatypeConverter.parseBase64Binary(certificate);
      CertificateFactory fac = CertificateFactory.getInstance("X509");
      InputStream in = new ByteArrayInputStream(decoded);
      X509Certificate cert = (X509Certificate) fac.generateCertificate(in);
      PublicKey publicKey = cert.getPublicKey();
      if (!cert.getIssuerDN().getName().equalsIgnoreCase(CN_SANDBOX_TPP)) {
        throw new UnknownCertificateException(ErrorReasonCode.UNKNOWN_CERTIFICATE.getReasonCode());
      } else {
        byte[] byteSignature = Base64.getDecoder().decode(signature);
        objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteSignature));
        certificateList = (List<byte[]>) objectInputStream.readObject();
        objectInputStream.close();

        return verifySignature(certificateList.get(0), certificateList.get(1), publicKey);
      }
    } catch (UnknownCertificateException e) {
      throw new UnknownCertificateException(ErrorReasonCode.UNKNOWN_CERTIFICATE.getReasonCode());
    } catch (Exception e) {

      logger.info("CheckValidCertificate getting exception : {}",
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


  private byte[] convertObjectIntoBytes(PaymentDetails paymentDetails) throws Exception {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream out = null;
    try {
      out = new ObjectOutputStream(bos);
      out.writeObject(paymentDetails);
      out.flush();
      return bos.toByteArray();
    } catch (Exception ex) {
      if (out != null) {
        out.close();
      }
      bos.close();
      throw new GeneralException(ex.getMessage());
    }

  }


}
