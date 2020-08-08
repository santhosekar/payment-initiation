package com.paymentinitiation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.paymentinitiation.model.PaymentDetails;
import com.paymentinitiation.model.ResponseCode;
import com.paymentinitiation.util.PaymentUtil;

@RestController
public class PaymentInitiationController {

  @Autowired
  public PaymentUtil paymentUtil;
  Logger logger = LoggerFactory.getLogger(PaymentInitiationController.class);

  @PostMapping(value = "/initiate-payment", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseCode> processRequest(@RequestBody PaymentDetails paymentDetails,
      @RequestHeader(value = "X-Request-Id") String requestId,
      @RequestHeader(value = "Signature-Certificate") String certificate,
      @RequestHeader(value = "Signature") String publicKey) throws Exception {
    logger.info("Entering processRequest");
    logger.debug("requestId-->" + requestId);

    return paymentUtil.isValidPaymentRequest(paymentDetails, certificate, publicKey);

  }

}

