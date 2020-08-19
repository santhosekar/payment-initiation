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
import com.paymentinitiation.model.PaymentDetails;

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
        paymentUtil.getViolations(getPaymentValue()).getValidationCount());
  }

  @Test(expected = InvalidRequestException.class)
  public void getViolationError() {
    Assert.assertEquals(Integer.valueOf("0"),
        paymentUtil.getViolations(getPaymentValueError()).getValidationCount());
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
    PaymentDetails paymentDetails = new PaymentDetails();
    paymentDetails.setDebtorIBAN("NL02RABO0000001555");
    paymentDetails.setCreditorIBAN("NL94ABNA1008270121");
    paymentDetails.setCurrency("EUR");
    paymentDetails.setAmount("1");
    String signature =
        "rO0ABXNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAACdwQAAAACdXIAAltCrPMX+AYIVOACAAB4cAAAAGQyOTMxOGUyNS1jZWJkLTQ5OGMtODg4YS1mNzc2NzJmNjY0NDk1OTcwY2I1MmYwNDMyZWI2NzBmMDM5YTAzMDI0OGYxMmU0MTkxOTE1NzViMjMwNTllOWEzMWQ3YWFlOTY0NDJhdXEAfgACAAABAHDSHFonyq8b9GF9nO2sK9NxfjkAnazw2QUQiNO/2i3R7bJdteM1pfO5RlkbIs6DaG7cHWJnQVmW7BpBey1s2ixo3QvqgAZ8RQ31TF9mJZcfHz8tMbQj7BF3e/Dr36WKz1jtV+Xa7kJtLO2Thlhjk5S2ca7wkvrYZhs9ecC6Xw9Rqk8m+5ws1sdhqHGRFhwiKeeowP/0wd7VzV09j3aVMZgcnTfws/urlAX6zuMM6hyumK1ENGTSpfDSJY8xNvJ6lPJ7dRlgpyQ3KG/8c0mLuNxkn61jWRMPxglT7bbzAvkAlq0hyt6zD4dH/QrBjz3NxqI89psfXRMM65iPaC3AulV4";
    String certificate =
        "LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tDQpNSUlDeXpDQ0FiT2dBd0lCQWdJRUh4eXhhREFOQmdrcWhraUc5dzBCQVFzRkFEQVdNUlF3RWdZRFZRUURFd3RUDQpZVzVrWW05NExWUlFVREFlRncweU1EQTRNVE14TlRJNU5ERmFGdzB5TVRBNE1UTXhOVEk1TkRGYU1CWXhGREFTDQpCZ05WQkFNVEMxTmhibVJpYjNndFZGQlFNSUlCSWpBTkJna3Foa2lHOXcwQkFRRUZBQU9DQVE4QU1JSUJDZ0tDDQpBUUVBcytEWmZnUG9Ec3RZY1Q0dndZeGhPa0FNQVhudDhsMWR6RTVZSTFqRmpyMCtHT05ycUxTVXE3cGFEa0JCDQo5d1RMbU9uZjlJdXVSek1GalI5aS9NUG5GK3UyU0Z5UW9lcnMxaVArTXErbFN5TXNBWCtrQk9BeEhLc00ycC84DQoveGdIcFhXSDFHNStPaWpTUGZFZmxOdFJGK25ITERFTVVsM2JSUzBmeEhsZjl3TEkxeGlLUWZ6bVA1eldENHM0DQp6djlnZ2JuQWdyWGNvbG8weForRDJEYlFrNms2ZERrQTR6OTFNS1Z5Sk9CRmZrWE0zLzhBalVTS0J3a3RUeWNyDQpVRGozWlBsRnhQMi8wNU5oenRFY2JjNk5Xc1plUG9NT3FqTkdwenNheGMrNTFZQ0Q3U3A2OHVNUEd2UjExNUwzDQphb3IwK1Flb1BkLzdiMzBLOVBWcnd2aldtd0lEQVFBQm95RXdIekFkQmdOVkhRNEVGZ1FVSUpXTnlOOFI1UDlHDQpxbU5XNzZuSnhOMnZJM1F3RFFZSktvWklodmNOQVFFTEJRQURnZ0VCQUZ0cGpJMWk1K1VvZDJmNnhqK01NQlM3DQpLeUwyd0ZtSE5lYmI3UGhxbFoxbHoydEV6aEkya0VCa3U1SEpha2Y0UTdzNUN6cnNqelkrOWxwcXFGUVVtT0FJDQpMNlFqTHAzQVNhbG14U2dPUlh0M1NNNmJXS2FEbHF4d2lBSHFzUUJqTnpVZUNNako5SHFHRHBPMEdxTHNSa3hnDQpVRUliNVpMRXdFTCtGYkZpdUtSZ3ljRjFTS3o0dis4bXZ0aFUrNVU2ZmJjTTU4UmZYMk9laVZ0YytOTEJUbGgvDQp4bU9wV3FWa0xNWWhHRFZCejVZVklqYnFMM3BRTUI0UUNyNTA1UjFrbmFaY0hQVVV0Zkdjbk16L25Db0FPVWlZDQpRbkY2VWliQVZwNWVjN0JpeERrV0dZa3lVdzFmT3gzTHk0RmhSeWtLTXdKc2NDcW1jNUFtWlFDSU1WNHRLU1U9DQotLS0tLUVORCBDRVJUSUZJQ0FURS0tLS0tDQo=";
    Assert.assertEquals(true,
        paymentUtil.isWhiteListed(signature, certificate, paymentDetails, "29318e25-cebd-498c-888a-f77672f66449"));
  }



  @Test
  public void validPaymentRequest() throws Exception {
    String signature =
            "rO0ABXNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAACdwQAAAACdXIAAltCrPMX+AYIVOACAAB4cAAAAGQyOTMxOGUyNS1jZWJkLTQ5OGMtODg4YS1mNzc2NzJmNjY0NDk1OTcwY2I1MmYwNDMyZWI2NzBmMDM5YTAzMDI0OGYxMmU0MTkxOTE1NzViMjMwNTllOWEzMWQ3YWFlOTY0NDJhdXEAfgACAAABAHDSHFonyq8b9GF9nO2sK9NxfjkAnazw2QUQiNO/2i3R7bJdteM1pfO5RlkbIs6DaG7cHWJnQVmW7BpBey1s2ixo3QvqgAZ8RQ31TF9mJZcfHz8tMbQj7BF3e/Dr36WKz1jtV+Xa7kJtLO2Thlhjk5S2ca7wkvrYZhs9ecC6Xw9Rqk8m+5ws1sdhqHGRFhwiKeeowP/0wd7VzV09j3aVMZgcnTfws/urlAX6zuMM6hyumK1ENGTSpfDSJY8xNvJ6lPJ7dRlgpyQ3KG/8c0mLuNxkn61jWRMPxglT7bbzAvkAlq0hyt6zD4dH/QrBjz3NxqI89psfXRMM65iPaC3AulV4";
    String certificate =
        "LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tDQpNSUlDeXpDQ0FiT2dBd0lCQWdJRUh4eXhhREFOQmdrcWhraUc5dzBCQVFzRkFEQVdNUlF3RWdZRFZRUURFd3RUDQpZVzVrWW05NExWUlFVREFlRncweU1EQTRNVE14TlRJNU5ERmFGdzB5TVRBNE1UTXhOVEk1TkRGYU1CWXhGREFTDQpCZ05WQkFNVEMxTmhibVJpYjNndFZGQlFNSUlCSWpBTkJna3Foa2lHOXcwQkFRRUZBQU9DQVE4QU1JSUJDZ0tDDQpBUUVBcytEWmZnUG9Ec3RZY1Q0dndZeGhPa0FNQVhudDhsMWR6RTVZSTFqRmpyMCtHT05ycUxTVXE3cGFEa0JCDQo5d1RMbU9uZjlJdXVSek1GalI5aS9NUG5GK3UyU0Z5UW9lcnMxaVArTXErbFN5TXNBWCtrQk9BeEhLc00ycC84DQoveGdIcFhXSDFHNStPaWpTUGZFZmxOdFJGK25ITERFTVVsM2JSUzBmeEhsZjl3TEkxeGlLUWZ6bVA1eldENHM0DQp6djlnZ2JuQWdyWGNvbG8weForRDJEYlFrNms2ZERrQTR6OTFNS1Z5Sk9CRmZrWE0zLzhBalVTS0J3a3RUeWNyDQpVRGozWlBsRnhQMi8wNU5oenRFY2JjNk5Xc1plUG9NT3FqTkdwenNheGMrNTFZQ0Q3U3A2OHVNUEd2UjExNUwzDQphb3IwK1Flb1BkLzdiMzBLOVBWcnd2aldtd0lEQVFBQm95RXdIekFkQmdOVkhRNEVGZ1FVSUpXTnlOOFI1UDlHDQpxbU5XNzZuSnhOMnZJM1F3RFFZSktvWklodmNOQVFFTEJRQURnZ0VCQUZ0cGpJMWk1K1VvZDJmNnhqK01NQlM3DQpLeUwyd0ZtSE5lYmI3UGhxbFoxbHoydEV6aEkya0VCa3U1SEpha2Y0UTdzNUN6cnNqelkrOWxwcXFGUVVtT0FJDQpMNlFqTHAzQVNhbG14U2dPUlh0M1NNNmJXS2FEbHF4d2lBSHFzUUJqTnpVZUNNako5SHFHRHBPMEdxTHNSa3hnDQpVRUliNVpMRXdFTCtGYkZpdUtSZ3ljRjFTS3o0dis4bXZ0aFUrNVU2ZmJjTTU4UmZYMk9laVZ0YytOTEJUbGgvDQp4bU9wV3FWa0xNWWhHRFZCejVZVklqYnFMM3BRTUI0UUNyNTA1UjFrbmFaY0hQVVV0Zkdjbk16L25Db0FPVWlZDQpRbkY2VWliQVZwNWVjN0JpeERrV0dZa3lVdzFmT3gzTHk0RmhSeWtLTXdKc2NDcW1jNUFtWlFDSU1WNHRLU1U9DQotLS0tLUVORCBDRVJUSUZJQ0FURS0tLS0tDQo=";
    Assert.assertEquals(201,
        paymentUtil
            .initiatePaymentRequest(getPaymentCorrect(), certificate, signature, "29318e25-cebd-498c-888a-f77672f66449")
            .getStatusCodeValue());


  }



}
