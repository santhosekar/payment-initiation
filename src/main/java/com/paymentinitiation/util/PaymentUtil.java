package com.paymentinitiation.util;

import static com.paymentinitiation.constant.PaymentInitiationConstant.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.paymentinitiation.enums.ErrorReasonCode;
import com.paymentinitiation.enums.TransactionStatus;
import com.paymentinitiation.exception.AmountLimitExceedException;
import com.paymentinitiation.exception.GeneralException;
import com.paymentinitiation.exception.InvalidRequestException;
import com.paymentinitiation.implementation.CertificateValidationImpl;
import com.paymentinitiation.model.PaymentDetails;
import com.paymentinitiation.model.ResponseCode;
import com.paymentinitiation.model.ValidationResponse;

@Component
public class PaymentUtil {
  @Autowired
  public CertificateValidationImpl certificateValidation;
  Logger logger = LoggerFactory.getLogger(PaymentUtil.class);
  String violationFields = null;

  public Integer getSumValue(String account) {
    logger.debug(ENTERING_METHOD_NAME_IS, "getSumValue");
    return Arrays.stream(account.split("")).filter(s -> s.matches("\\d+"))
        .mapToInt(Integer::valueOf).sum();
  }

  public ValidationResponse getViolations(PaymentDetails paymentDetails) {
    logger.debug(ENTERING_METHOD_NAME_IS, "getViolationsCount");
    violationFields = "Following fields are not valid: ";
    ValidationResponse validationResponse = new ValidationResponse();
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<PaymentDetails>> violations = validator.validate(paymentDetails);
    violations.forEach(paymentDetailsConstraintViolation -> violationFields +=
        paymentDetailsConstraintViolation.getPropertyPath() + ",");
    if (!violations.isEmpty()) {
      logger.info("ValidationModel getting exception : {}", violationFields);
      throw new InvalidRequestException(violationFields);
    } else {
      validationResponse.setValidationCount(0);
      return validationResponse;
    }
  }

  public boolean isWhiteListed(String certificate, String publicKey) throws IOException {
    logger.debug(ENTERING_METHOD_NAME_IS, "isWhiteListed");
    return certificateValidation.checkWhiteListedCertificate(certificate, publicKey);
  }

  public boolean isValidLimit(PaymentDetails paymentDetails) {
    double amount = Double.parseDouble(paymentDetails.getAmount());
    String debitIban = paymentDetails.getDebtorIBAN().replaceAll(REGEX, "");
    if (amount > 0 && (getSumValue(debitIban) % paymentDetails.getDebtorIBAN().length()) == 0) {
      return true;
    } else {
      throw new AmountLimitExceedException(ErrorReasonCode.LIMIT_EXCEEDED.getReasonCode());
    }
  }

  public ResponseEntity<ResponseCode> isValidPaymentRequest(PaymentDetails paymentDetails,
      String certificate, String publicKey) throws IOException {
    logger.debug(ENTERING_METHOD_NAME_IS, "isValidPaymentRequest");
    ValidationResponse validationResponse;
    validationResponse = getViolations(paymentDetails);
    if (validationResponse != null && validationResponse.getValidationCount() == 0
        && isWhiteListed(certificate, publicKey) && isValidLimit(paymentDetails)) {
      return new ResponseEntity<>(
          new ResponseCode(TRANSACTION_CODE, TransactionStatus.ACCEPTED.getStatus()),
          HttpStatus.CREATED);
    } else {
      throw new GeneralException(ErrorReasonCode.GENERAL_ERROR.getReasonCode());
    }

  }


}
