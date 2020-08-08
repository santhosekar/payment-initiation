package com.paymentinitiation.exception;

import static com.paymentinitiation.constant.PaymentInitiationConstant.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {



  @ExceptionHandler(InvalidRequestException.class)
  public final ResponseEntity<ExceptionResponse> handleInvalid(InvalidRequestException ex,
      WebRequest request) {

    ExceptionResponse error = new ExceptionResponse();
    error.setReason(ex.getMessage());
    error.setReasonCode(INVALID_REASON_CODE);
    error.setStatus(REJECTED);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(UnknownCertificateException.class)
  public final ResponseEntity<ExceptionResponse> handleUnknownCertificateException(
      UnknownCertificateException ex, WebRequest request) {

    ExceptionResponse error = new ExceptionResponse();
    error.setReason(ex.getMessage());
    error.setReasonCode(UNKNOWN_CERTIFICATE);
    error.setStatus(REJECTED);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AmountLimitExceedException.class)
  public final ResponseEntity<ExceptionResponse> handleUnknownAmount(AmountLimitExceedException ex,
      WebRequest request) {

    ExceptionResponse error = new ExceptionResponse();
    error.setReason(ex.getMessage());
    error.setReasonCode(LIMIT_EXCEEDED);
    error.setStatus(REJECTED);
    return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
  }


  @ExceptionHandler(InvalidCertificateException.class)
  public final ResponseEntity<ExceptionResponse> handleInvalid(InvalidCertificateException ex,
      WebRequest request) {

    ExceptionResponse error = new ExceptionResponse();
    error.setReason(ex.getMessage());
    error.setReasonCode(INVALID_SIGNATURE);
    error.setStatus(REJECTED);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ExceptionResponse> handleGeneralException(Exception ex,
      WebRequest request) {

    ExceptionResponse error = new ExceptionResponse();
    error.setReason(ex.getMessage());
    error.setReasonCode(INTERNAL_SERVER_ERROR);
    error.setStatus(REJECTED);
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }


}
