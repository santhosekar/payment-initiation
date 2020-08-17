package com.paymentinitiation.implementation;

import static com.paymentinitiation.constant.PaymentInitiationConstant.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.List;

import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paymentinitiation.enums.ErrorReasonCode;
import com.paymentinitiation.exception.GeneralException;
import com.paymentinitiation.exception.InvalidCertificateException;
import com.paymentinitiation.exception.UnknownCertificateException;
import com.paymentinitiation.model.PaymentDetails;
import com.paymentinitiation.service.CertificateValidation;
import com.paymentinitiation.util.PaymentUtil;

@Component
public class CertificateValidationImpl implements CertificateValidation {

  Logger logger = LoggerFactory.getLogger(CertificateValidationImpl.class);

  List<byte[]> certificateList;
  CertificateFactory certificateFactory;
  @Autowired
  PaymentUtil paymentUtil;

  @Override
  public boolean checkValidCertificate(String certificate, String signature,
      PaymentDetails paymentDetails, String paymentId) throws IOException {
    logger.debug("Entering method name is : {}", "CheckValidCertificate");
    ObjectInputStream objectInputStream = null;
    String commonName = null;
    try {
      byte[] decoded = DatatypeConverter.parseBase64Binary(certificate);
      CertificateFactory fac = CertificateFactory.getInstance(X509);
      InputStream in = new ByteArrayInputStream(decoded);
      X509Certificate cert = (X509Certificate) fac.generateCertificate(in);
      PublicKey publicKey = cert.getPublicKey();
      LdapName ln = new LdapName(cert.getIssuerDN().getName());
      for(Rdn rdn : ln.getRdns()) {
        if(rdn.getType().equalsIgnoreCase(CN)) {
           commonName=rdn.getValue().toString();
          break;
        }
      }
      if (!commonName.equalsIgnoreCase(CN_SANDBOX_TPP)) {
        throw new UnknownCertificateException(ErrorReasonCode.UNKNOWN_CERTIFICATE.getReasonCode());
      } else {
        byte[] byteSignature = Base64.getDecoder().decode(signature);
        objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteSignature));
        certificateList = (List<byte[]>) objectInputStream.readObject();
        objectInputStream.close();
        if (verifySignature(certificateList.get(0), certificateList.get(1), publicKey)) {

          return paymentUtil.checkCorrectHashValue(paymentDetails, paymentId,
              new String(certificateList.get(0)));
        } else {
          throw new InvalidCertificateException(ErrorReasonCode.INVALID_SIGNATURE.getReasonCode());
        }
      }
    } catch (UnknownCertificateException e) {
      throw new UnknownCertificateException(ErrorReasonCode.UNKNOWN_CERTIFICATE.getReasonCode());
    } catch (InvalidCertificateException e) {
      throw new InvalidCertificateException(ErrorReasonCode.INVALID_SIGNATURE.getReasonCode());
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



}
