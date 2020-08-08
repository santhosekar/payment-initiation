package com.paymentinitiation.util;

import static com.paymentinitiation.factory.PaymentInitiationFactory.*;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.paymentinitiation.exception.AmountLimitExceedException;
import com.paymentinitiation.exception.InvalidRequestException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PaymentUtilTest {

  @Autowired
  public PaymentUtil paymentUtil;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void getSumValue() {
    Assert.assertEquals(Integer.valueOf("16"), paymentUtil.getSumValue("NL02RABO0000004550"));
  }

  @Test
  public void getViolation() {
    Assert.assertEquals(Integer.valueOf("0"),
        paymentUtil.getViolationsCount(getPaymentValue()).getValidationCount());
  }

  @Test(expected = InvalidRequestException.class)
  public void getViolationError() {
    Assert.assertEquals(Integer.valueOf("0"),
        paymentUtil.getViolationsCount(getPaymentValueError()).getValidationCount());
  }

  @Test(expected = AmountLimitExceedException.class)
  public void getLimitValue() {
    Assert.assertTrue(paymentUtil.isValidLimit(getPaymentValue()));
  }

  @Test
  public void getLimit() {
    Assert.assertTrue(paymentUtil.isValidLimit(getPaymentCorrect()));
  }

  @Test
  public void getCertificate() throws Exception {
    String public_signature =
        "rO0ABXNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAACdwQAAAACdXIAAltCrPMX+AYIVOACAAB4cAAAABE2Mis2NTY1KyxkYXNkYXNkYXVxAH4AAgAAAICMRIdu+U7jG74rtVBlGiHxErMYluIMP8PH3vSh9aYSImgIu0qGS3UeRyBTjNmds/07Sxna/qLfsbfgXu2sT03v0J6x+X28e1Fv79fWaigwsKQfD2nM/2Z4tPT1iW46eDBWOjgGkZE6jbOW8/2PlArCipsIkj0GghQqRrLCYoNdpng=";
    String public_key =
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKm2+hmexuHrDavwMJ6z90oCoLshmtvdlpqpqQS0bIBCpzKIr8f8dwj5+E0cnrNvBRplf/DkfnbnbLyF3YBYlhjOFVjJ/2JhHrqIgJt45QmbTbvYh8AEJ8Wlr1vn4eJL3xWrlh8J39e9FeH4ZGQnQUMh+Uj7FNaXW+a1NSRYCVJQIDAQAB";
    Assert.assertEquals(true, paymentUtil.isWhiteListed(public_key, public_signature));
  }



  @Test
  public void validPaymentRequest() throws Exception {
    String public_signature =
        "rO0ABXNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAACdwQAAAACdXIAAltCrPMX+AYIVOACAAB4cAAAABE2Mis2NTY1KyxkYXNkYXNkYXVxAH4AAgAAAICMRIdu+U7jG74rtVBlGiHxErMYluIMP8PH3vSh9aYSImgIu0qGS3UeRyBTjNmds/07Sxna/qLfsbfgXu2sT03v0J6x+X28e1Fv79fWaigwsKQfD2nM/2Z4tPT1iW46eDBWOjgGkZE6jbOW8/2PlArCipsIkj0GghQqRrLCYoNdpng=";
    String public_key =
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKm2+hmexuHrDavwMJ6z90oCoLshmtvdlpqpqQS0bIBCpzKIr8f8dwj5+E0cnrNvBRplf/DkfnbnbLyF3YBYlhjOFVjJ/2JhHrqIgJt45QmbTbvYh8AEJ8Wlr1vn4eJL3xWrlh8J39e9FeH4ZGQnQUMh+Uj7FNaXW+a1NSRYCVJQIDAQAB";

    Assert.assertEquals(201,
        paymentUtil.isValidPaymentRequest(getPaymentCorrect(), public_key, public_signature)
            .getStatusCodeValue());


  }



}
