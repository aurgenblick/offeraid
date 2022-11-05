package com.lisovenko.offeraid.processing;

import com.lisovenko.offeraid.controllers.UserRegisterForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator
    implements ConstraintValidator<PasswordsMatch, UserRegisterForm> {
  @Override
  public void initialize(PasswordsMatch constraintAnnotation) {
    // intentionally empty
  }

  @Override
  public boolean isValid(UserRegisterForm value, ConstraintValidatorContext context) {
    if (value.getPassword().equals(value.getPasswordRepeated())) {
      return true;
    }

    context.disableDefaultConstraintViolation();
    context
        .buildConstraintViolationWithTemplate("{PasswordsNotMatching}")
        .addPropertyNode("passwordRepeated")
        .addConstraintViolation();
    return false;
  }
}
