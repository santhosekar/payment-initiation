package com.paymentinitiation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentinitiation.exception.InvalidRequestException;
import com.paymentinitiation.model.PaymentDetails;
import com.paymentinitiation.model.ValidationModel;
import com.paymentinitiation.util.PaymentUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static com.paymentinitiation.factory.PaymentInitiationFactory.getPaymentValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
        InvalidRequestException.class
})


public class PaymentInitiationControllerTest {
  private MockMvc mockMvc;

  @InjectMocks
  private PaymentInitiationController paymentInitiationController;

  @Mock
  PaymentUtil paymentUtil;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(paymentInitiationController).build();

  }

  @Test
  public void testCreated() throws Exception {

    ValidationModel validationModel=new ValidationModel();
    validationModel.setValidationCount(0);
    Mockito.when(paymentUtil.getViolationsCount(Mockito.any(PaymentDetails.class)))
            .thenReturn(validationModel);
    Mockito.when(paymentUtil.isWhiteListed(Mockito.any(String.class),Mockito.any(String.class)))
            .thenReturn(true);
    Mockito.when(paymentUtil.isValidLimit(Mockito.any(PaymentDetails.class)))
            .thenReturn(true);
    String inputJson = mapToJson(getPaymentValue());
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headers.add("X-Request-Id","212122");
    headers.add("Signature-Certificate", "26");
    headers.add("Signature", "665");
    mockMvc
            .perform(MockMvcRequestBuilders.post("/initiate-payment").content(inputJson).headers(headers)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());




  }
  @Test(expected = Exception.class)
  public void testCreatedtoNull() throws Exception {

    ValidationModel validationModel=new ValidationModel();
    validationModel.setValidationCount(1);
    Mockito.when(paymentUtil.getViolationsCount(Mockito.any(PaymentDetails.class)))
            .thenReturn(validationModel);
    Mockito.when(paymentUtil.isWhiteListed(Mockito.any(String.class),Mockito.any(String.class)))
            .thenReturn(true);
    Mockito.when(paymentUtil.isValidLimit(Mockito.any(PaymentDetails.class)))
            .thenReturn(true);
    String inputJson = mapToJson(getPaymentValue());
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headers.add("X-Request-Id","212122");
    headers.add("Signature-Certificate", "26");
    headers.add("Signature", "665");
    mockMvc
            .perform(MockMvcRequestBuilders.post("/initiate-payment").content(inputJson).headers(headers)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());




  }



  protected String mapToJson(Object obj) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(obj);
  }

}
