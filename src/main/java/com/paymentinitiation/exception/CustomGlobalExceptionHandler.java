package com.paymentinitiation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.paymentinitiation.enums.ErrorReasonCode;
import com.paymentinitiation.enums.TransactionStatus;


@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {



  @ExceptionHandler(InvalidRequestException.class)
  public final ResponseEntity<ExceptionResponse> handleInvalidException(InvalidRequestException ex,
      WebRequest request) {

    ExceptionResponse error = new ExceptionResponse();
    error.setReason(ex.getMessage());
    error.setReasonCode(ErrorReasonCode.INVALID_REQUEST.getReasonCode());
    error.setStatus(TransactionStatus.REJECTED.getStatus());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(UnknownCertificateException.class)
  public final ResponseEntity<ExceptionResponse> handleUnknownCertificateException(
      UnknownCertificateException ex, WebRequest request) {

    ExceptionResponse error = new ExceptionResponse();
    error.setReason(ex.getMessage());
    error.setReasonCode(ErrorReasonCode.UNKNOWN_CERTIFICATE.getReasonCode());
    error.setStatus(TransactionStatus.REJECTED.getStatus());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AmountLimitExceedException.class)
  public final ResponseEntity<ExceptionResponse> handleAmountLimitExceedException(
      AmountLimitExceedException ex, WebRequest request) {

    ExceptionResponse error = new ExceptionResponse();
    error.setReason(ex.getMessage());
    error.setReasonCode(ErrorReasonCode.LIMIT_EXCEEDED.getReasonCode());
    error.setStatus(TransactionStatus.REJECTED.getStatus());
    return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
  }


  @ExceptionHandler(InvalidCertificateException.class)
  public final ResponseEntity<ExceptionResponse> handleInvalidCertificateException(
      InvalidCertificateException ex, WebRequest request) {

    ExceptionResponse error = new ExceptionResponse();
    error.setReason(ex.getMessage());
    error.setReasonCode(ErrorReasonCode.INVALID_SIGNATURE.getReasonCode());
    error.setStatus(TransactionStatus.REJECTED.getStatus());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(GeneralException.class)
  public final ResponseEntity<ExceptionResponse> handleGeneralException(GeneralException ex,
      WebRequest request) {

    ExceptionResponse error = new ExceptionResponse();
    error.setReason(ex.getMessage());
    error.setReasonCode(ErrorReasonCode.GENERAL_ERROR.getReasonCode());
    error.setStatus(TransactionStatus.REJECTED.getStatus());
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ExceptionResponse> handleCommonException(Exception ex,
      WebRequest request) {

    ExceptionResponse error = new ExceptionResponse();
    error.setReason(ex.getMessage());
    error.setReasonCode(ErrorReasonCode.GENERAL_ERROR.getReasonCode());
    error.setStatus(TransactionStatus.REJECTED.getStatus());
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }



}
