package com.paymentinitiation.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDetails {
  @NotNull
  @Pattern(regexp = "[A-Z]{2}[0-9]{2}[a-zA-Z0-9]{1,30}", message = "INVALID_REQUEST")
  private String debtorIBAN;
  @NotNull
  @Pattern(regexp = "[A-Z]{2}[0-9]{2}[a-zA-Z0-9]{1,30}", message = "INVALID_REQUEST")
  @NotNull
  private String creditorIBAN;
  @Pattern(regexp = "-?[0-9]+(\\.[0-9]{1,3})?", message = "INVALID_REQUEST")
  @NotNull
  private String amount;
  @Pattern(regexp = "[A-Z]{3}", message = "INVALID_REQUEST")
  private String currency;
  private String endToEndId;

}
