package com.paymentinitiation.controller;

import com.paymentinitiation.model.PaymentDetails;
import com.paymentinitiation.model.ResponseCode;
import com.paymentinitiation.model.ValidationModel;
import com.paymentinitiation.util.PaymentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import static com.paymentinitiation.constant.PaymentInitiationConstant.*;

@RestController
public class PaymentInitiationController {

  @Autowired
  public PaymentUtil paymentUtil;
  public ValidationModel validationModel;
  Logger logger = LoggerFactory.getLogger(PaymentInitiationController.class);

  @PostMapping(value = "/initiate-payment", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseCode> processRequest(@RequestBody PaymentDetails paymentDetails,
      @RequestHeader(value = "X-Request-Id") String requestId,
      @RequestHeader(value = "Signature-Certificate") String certificate,
      @RequestHeader(value = "Signature") String publicKey) throws Exception {
    logger.info("Entering processRequest");
    logger.debug("requestId-->" + requestId);
    validationModel = paymentUtil.getViolationsCount(paymentDetails);
    if (validationModel != null && validationModel.getValidationCount() == 0
        && paymentUtil.isWhiteListed(certificate, publicKey)
        && paymentUtil.isValidLimit(paymentDetails)) {
      return new ResponseEntity<>(new ResponseCode(TRANSACTION_CODE, ACCEPTED), HttpStatus.CREATED);
    } else {
      throw new Exception(INTERNAL_SERVER_ERROR);
    }
  }

}

