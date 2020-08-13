package com.paymentinitiation.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationResponse {
  private Integer validationCount;
  private String validationMessage;
}
