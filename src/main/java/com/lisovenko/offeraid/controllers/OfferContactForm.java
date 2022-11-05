package com.lisovenko.offeraid.controllers;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class OfferContactForm {
  @NotBlank
  @Size(max = 1000)
  private String message;
}
