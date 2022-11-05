package com.lisovenko.offeraid.controllers;

import com.lisovenko.offeraid.processing.PasswordsMatch;
import com.lisovenko.offeraid.processing.UserNotExist;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@UserNotExist
@PasswordsMatch
@Getter
@Setter
public class UserRegisterForm {
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
    return "UserRegisterForm{name='%s', email='%s'}".formatted(name, email);
  }
}
