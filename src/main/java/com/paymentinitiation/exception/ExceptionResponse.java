package com.paymentinitiation.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {
  private String status;
  private String reason;
  private String reasonCode;
}
