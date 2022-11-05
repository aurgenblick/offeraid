package com.lisovenko.offeraid.controllers;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegisterRequestController {
  @NotBlank
  @Size(max = 120)
  private String name;

  @NotBlank
  @Email
  @Size(max = 120)
  private String email;

  @NotBlank private String password;
  @NotBlank private String passwordRepeated;

  @Override
  public String toString() {
    return String.format("UserRegisterForm{name='%s', email='%s'}", name, email);
  }

  //@TODO implement validation for password and username
}
