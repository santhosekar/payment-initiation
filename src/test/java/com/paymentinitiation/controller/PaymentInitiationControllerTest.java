package com.paymentinitiation.controller;

import static com.paymentinitiation.factory.PaymentInitiationFactory.getPaymentValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentinitiation.exception.InvalidRequestException;
import com.paymentinitiation.model.PaymentDetails;
import com.paymentinitiation.model.ResponseCode;
import com.paymentinitiation.util.PaymentUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {InvalidRequestException.class})


public class PaymentInitiationControllerTest {
  @Mock
  PaymentUtil paymentUtil;
  private MockMvc mockMvc;
  @InjectMocks
  private PaymentInitiationController paymentInitiationController;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(paymentInitiationController).build();

  }

  @Test
  public void testCreated() throws Exception {

    ResponseEntity<ResponseCode> responseCode =
        new ResponseEntity("{ \"paymentId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n"
            + "  \"status\": \"Accepted\"}", HttpStatus.CREATED);
    Mockito.when(paymentUtil.isValidPaymentRequest(Mockito.any(PaymentDetails.class),
        Mockito.any(String.class), Mockito.any(String.class))).thenReturn(responseCode);
    String inputJson = mapToJson(getPaymentValue());
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headers.add("X-Request-Id", "212122");
    headers.add("Signature-Certificate", "26");
    headers.add("Signature", "665");
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/initiate-payment").content(inputJson).headers(headers)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());



  }



  protected String mapToJson(Object obj) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(obj);
  }

}
