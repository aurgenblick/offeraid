package com.lisovenko.offeraid.controllers;

import com.lisovenko.offeraid.entities.DTOs.OfferDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class OfferForm {
  private Integer id;

  @NotBlank
  @Size(max = 255)
  private String title;

  @NotBlank
  @Size(max = 2000)
  private String body;
  private boolean active;
  private String userEmail;
  @Positive
  private int areaId;
  @Positive
  private int categoryId;

  public OfferForm(OfferDTO offer) {
    id = offer.id();
    title = offer.title();
    body = offer.body();
    active = offer.active();
    userEmail = offer.user().email();
    areaId = offer.area().id();
    categoryId = offer.category().id();
  }
}
