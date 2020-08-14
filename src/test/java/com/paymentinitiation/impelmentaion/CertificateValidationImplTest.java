package com.paymentinitiation.impelmentaion;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.util.Base64;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paymentinitiation.exception.UnknownCertificateException;
import com.paymentinitiation.implementation.CertificateValidationImpl;
import com.paymentinitiation.model.PaymentDetails;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CertificateValidationImplTest {


  @Autowired
  public CertificateValidationImpl certificateValidation;
  @Autowired
  public SignUtil signUtil;
  String encodedSignature;
  String encodedPublicKey =
      "LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tDQpNSUlDeXpDQ0FiT2dBd0lCQWdJRUh4eXhhREFOQmdrcWhraUc5dzBCQVFzRkFEQVdNUlF3RWdZRFZRUURFd3RUDQpZVzVrWW05NExWUlFVREFlRncweU1EQTRNVE14TlRJNU5ERmFGdzB5TVRBNE1UTXhOVEk1TkRGYU1CWXhGREFTDQpCZ05WQkFNVEMxTmhibVJpYjNndFZGQlFNSUlCSWpBTkJna3Foa2lHOXcwQkFRRUZBQU9DQVE4QU1JSUJDZ0tDDQpBUUVBcytEWmZnUG9Ec3RZY1Q0dndZeGhPa0FNQVhudDhsMWR6RTVZSTFqRmpyMCtHT05ycUxTVXE3cGFEa0JCDQo5d1RMbU9uZjlJdXVSek1GalI5aS9NUG5GK3UyU0Z5UW9lcnMxaVArTXErbFN5TXNBWCtrQk9BeEhLc00ycC84DQoveGdIcFhXSDFHNStPaWpTUGZFZmxOdFJGK25ITERFTVVsM2JSUzBmeEhsZjl3TEkxeGlLUWZ6bVA1eldENHM0DQp6djlnZ2JuQWdyWGNvbG8weForRDJEYlFrNms2ZERrQTR6OTFNS1Z5Sk9CRmZrWE0zLzhBalVTS0J3a3RUeWNyDQpVRGozWlBsRnhQMi8wNU5oenRFY2JjNk5Xc1plUG9NT3FqTkdwenNheGMrNTFZQ0Q3U3A2OHVNUEd2UjExNUwzDQphb3IwK1Flb1BkLzdiMzBLOVBWcnd2aldtd0lEQVFBQm95RXdIekFkQmdOVkhRNEVGZ1FVSUpXTnlOOFI1UDlHDQpxbU5XNzZuSnhOMnZJM1F3RFFZSktvWklodmNOQVFFTEJRQURnZ0VCQUZ0cGpJMWk1K1VvZDJmNnhqK01NQlM3DQpLeUwyd0ZtSE5lYmI3UGhxbFoxbHoydEV6aEkya0VCa3U1SEpha2Y0UTdzNUN6cnNqelkrOWxwcXFGUVVtT0FJDQpMNlFqTHAzQVNhbG14U2dPUlh0M1NNNmJXS2FEbHF4d2lBSHFzUUJqTnpVZUNNako5SHFHRHBPMEdxTHNSa3hnDQpVRUliNVpMRXdFTCtGYkZpdUtSZ3ljRjFTS3o0dis4bXZ0aFUrNVU2ZmJjTTU4UmZYMk9laVZ0YytOTEJUbGgvDQp4bU9wV3FWa0xNWWhHRFZCejVZVklqYnFMM3BRTUI0UUNyNTA1UjFrbmFaY0hQVVV0Zkdjbk16L25Db0FPVWlZDQpRbkY2VWliQVZwNWVjN0JpeERrV0dZa3lVdzFmT3gzTHk0RmhSeWtLTXdKc2NDcW1jNUFtWlFDSU1WNHRLU1U9DQotLS0tLUVORCBDRVJUSUZJQ0FURS0tLS0tDQo=";
  String wrongPublicKey =
      "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBg55CK6Bth0t3uFhE6m+zhh2efcB3xmuQa9Ee5TAOHUWwrnv8Nj/ZLSDirGTh1s/9goigHH37rokzUwu5wZg/EOsM7NbSuespqwOC2aXqzUW4Bi7E/29UidFdqoo7E91XAgSOhmkmq54RE5Krd4O/WvyjH1Ue8LiJaz82yX2w68vicwIDAQAB";
  PaymentDetails paymentDetails;
  String otherCNPublicKey =
      "LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tDQpNSUlDeFRDQ0FhMmdBd0lCQWdJRVJPSEI2VEFOQmdrcWhraUc5dzBCQVFzRkFEQVRNUkV3RHdZRFZRUURFd2hDDQpZV1ZzWkhWdVp6QWVGdzB5TURBNE1UUXhNRFEyTWpOYUZ3MHlNVEE0TVRReE1EUTJNak5hTUJNeEVUQVBCZ05WDQpCQU1UQ0VKaFpXeGtkVzVuTUlJQklqQU5CZ2txaGtpRzl3MEJBUUVGQUFPQ0FROEFNSUlCQ2dLQ0FRRUFoVmk0DQprTnZFS25pR1B1R2h0cVVzelRCL1VqTDY4cDV1MDNCMVBRMFd5Y2xFcEF6TUpyMmJqdk5BaENnYVY0MWVrcVVEDQpFSGlXR2srSXpZbklDK0xTWTRNYWhiQTRmRW41cEM3eDlsbjJYdDJkN2UvZTIzSy9iSWRYZ1NsaWhmeUVKYTFtDQptOVZkeFJ6Y2ZsalAvK0RDRnkrMVhQcFFNV1FLRko0Wmdoa1ZhN0tDR3NsVHZVSnArWWI5ZjhxcjI2UFlvVEZNDQpzbE5xQm5pT1RmY0lOa0xhc0Mxekk2VWp5VUdDOFZZNWxwMmdOTCtvdUM3WEZEdXNmWGZQMVRBY2YvMFJ1bEhVDQpNRGJ4UHlRUnJObHNCVUEydndGaFp2K1Rsays5ejN0ZzVSNENuVGFzU2hsWkZBVXd0Qms2eVBWUTNEZk5pU3NwDQpETDhkTTFqclVpTkZwdEo3d3dJREFRQUJveUV3SHpBZEJnTlZIUTRFRmdRVXY3bzdwVTY4K1o3dWY5MnY5aFhFDQptTFM2dXF3d0RRWUpLb1pJaHZjTkFRRUxCUUFEZ2dFQkFEaEJLQmtwQUJ5TnZsWWErVldzOGlWSG5jNjMwVFd1DQpjVjdyMVNpcDFNVEZWL1R0bXd2VzBCU29HU012QWJxNGZ5eGs2cTBJUGh5VFFtekVJT2IxeE5HS3BrdWJTK0ZjDQpONHlJdWQvVElaY2txZTlqYjRVVUlnQVFTMkxKU2tyQ2piUU1uVXlTaGw0OWFtYXBVdm1VTzVPcVMvT205NERwDQpyTm03UVZVK2I0bkgxMWNRK2txdTY2ZDJFazBxZlhKUUdDbEJIVGg2T29Cd01pT1dYT3B1aGhlQklSNDlBYlpZDQp0MkpBUm9pcmFDcU1vbjlwRXlIbkhBWjBVMlM0ZTNVTlhETmd2QmY5WmFFR1VXNnJjRjFZWGhTYTY2VGRjVDBZDQozdVpYL21ZNVdQTm9vSXhUaUduMjJBYVBObS9uM1MzWUVyd0JjdzMzNTFDQWMvejNteXNEbUxNPQ0KLS0tLS1FTkQgQ0VSVElGSUNBVEUtLS0tLQ0K";

  @Before
  public void setup() throws Exception {
    KeyStore keyStore = KeyStore.getInstance("PKCS12");
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("sender_keystore.p12").getFile());

    keyStore.load(new FileInputStream(file), "changeit".toCharArray());
    PrivateKey privateKey = (PrivateKey) keyStore.getKey("senderKeyPair", "changeit".toCharArray());

    MessageDigest md = MessageDigest.getInstance("SHA-256");
    PaymentDetails paymentDetails = new PaymentDetails();
    paymentDetails.setDebtorIBAN("NL02RABO0000001555");
    paymentDetails.setCreditorIBAN("NL94ABNA1008270121");
    paymentDetails.setCurrency("EUR");
    paymentDetails.setAmount("1");
    md.update(signUtil.convertObjectIntoBytes(paymentDetails));
    byte[] messageHash = md.digest();
    String concat = "123-123-456" + messageHash;
    byte[] signature = signUtil.message(concat, privateKey);
    encodedSignature = Base64.getEncoder().encodeToString(signature);



  }

  @Test
  public void checkWhiteListedCertificate() throws Exception {

    assert (certificateValidation.CheckValidCertificate(encodedPublicKey, encodedSignature,
        paymentDetails, "123-123-456"));

  }

  @Test(expected = Exception.class)
  public void checkWhiteListedImproper() throws Exception {

    assert (certificateValidation.CheckValidCertificate(wrongPublicKey, encodedSignature,
        paymentDetails, "123-123-456"));

  }

  @Test(expected = UnknownCertificateException.class)
  public void checkWhiteOtherCN() throws Exception {

    assert (certificateValidation.CheckValidCertificate(otherCNPublicKey, encodedSignature,
        paymentDetails, "123-123-456"));

  }



}
