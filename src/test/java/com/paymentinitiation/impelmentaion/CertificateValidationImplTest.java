package com.paymentinitiation.impelmentaion;

import java.security.*;
import java.util.Base64;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paymentinitiation.implementation.CertificateValidationImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CertificateValidationImplTest {


  @Autowired
  public CertificateValidationImpl certificateValidation;
  @Autowired
  public SignUtil signUtil;
  String encodedSignature;
  String encodedPublicKey;
  String wrongPublicKey =
      "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBg55CK6Bth0t3uFhE6m+zhh2efcB3xmuQa9Ee5TAOHUWwrnv8Nj/ZLSDirGTh1s/9goigHH37rokzUwu5wZg/EOsM7NbSuespqwOC2aXqzUW4Bi7E/29UidFdqoo7E91XAgSOhmkmq54RE5Krd4O/WvyjH1Ue8LiJaz82yX2w68vicwIDAQAB";
  private PrivateKey privateKey;
  private PublicKey publicKey;

  @Before
  public void setup() throws Exception {
    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
    keyGen.initialize(1024);
    KeyPair pair = keyGen.generateKeyPair();
    this.privateKey = pair.getPrivate();
    this.publicKey = pair.getPublic();
    Signature sign = Signature.getInstance("SHA1withRSA");
    sign.initSign(this.privateKey);
    String concat = "62+6565+" + "," + "dasda";
    byte[] signature = signUtil.message(concat, privateKey);
    encodedSignature = Base64.getEncoder().encodeToString(signature);
    encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());


  }

  @Test
  public void checkWhiteListedCertificate() throws Exception {

    assert (certificateValidation.checkWhiteListedCertificate(encodedPublicKey, encodedSignature));

  }

  @Test(expected = Exception.class)
  public void checkWhiteListedImproper() throws Exception {

    assert (certificateValidation.checkWhiteListedCertificate(wrongPublicKey, encodedSignature));

  }



}
