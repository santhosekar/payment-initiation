package com.paymentinitiation.util;

import static com.paymentinitiation.constant.PaymentInitiationConstant.LIMIT_EXCEEDED;
import static com.paymentinitiation.constant.PaymentInitiationConstant.REGEX;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paymentinitiation.exception.AmountLimitExceedException;
import com.paymentinitiation.exception.InvalidRequestException;
import com.paymentinitiation.implementation.CertificateValidationImpl;
import com.paymentinitiation.model.PaymentDetails;
import com.paymentinitiation.model.ValidationModel;

@Component
public class PaymentUtil {
  @Autowired
  public CertificateValidationImpl certificateValidation;
  Logger logger = LoggerFactory.getLogger(PaymentUtil.class);
  String violationFields=null;
  public Integer getSumValue(String num) {
    logger.info("Entering getSumValue");
    char[] numbers = num.toCharArray();
    int sum = 0;
    for (char a : numbers) {
      if (Character.isDigit(a)) {
        String s1 = Character.toString(a);
        int i1 = Integer.parseInt(s1);

        sum = sum + i1;
      }
    }

    return sum;
  }

  public ValidationModel getViolationsCount(PaymentDetails paymentDetails) {

    violationFields = "Following fields are not valid: ";
    ValidationModel validationModel = new ValidationModel();
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<PaymentDetails>> violations = validator.validate(paymentDetails);
    violations.forEach(paymentDetailsConstraintViolation -> violationFields +=
        paymentDetailsConstraintViolation.getPropertyPath() + ",");
    if (violations.size() > 0) {
      throw new InvalidRequestException(violationFields);
    } else {
      validationModel.setValidationCount(0);
      logger.debug("violations-->" + violations.size());
      return validationModel;
    }
  }

  public boolean isWhiteListed(String certificate, String publicKey) throws Exception {
    return certificateValidation.checkWhiteListedCertificate(certificate, publicKey);
  }

  public boolean isValidLimit(PaymentDetails paymentDetails) {
    double amount = Double.parseDouble(paymentDetails.getAmount());
    String debitIban = paymentDetails.getDebtorIBAN().replaceAll(REGEX, "");
    if (amount > 0 && (getSumValue(debitIban) % paymentDetails.getDebtorIBAN().length()) == 0) {
      return true;
    } else {
      throw new AmountLimitExceedException(LIMIT_EXCEEDED);
    }
  }
}
