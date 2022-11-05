package com.lisovenko.offeraid.processing;

import com.lisovenko.offeraid.controllers.UserRegisterForm;
import com.lisovenko.offeraid.services.UserService;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UserNotExistValidator implements ConstraintValidator<UserNotExist, UserRegisterForm> {
  private final UserService userService;

  public void initialize(UserNotExist constraint) {
    // intentionally empty
  }

  public boolean isValid(UserRegisterForm formData, ConstraintValidatorContext context) {
    if (!userService.userExistsByEmail(formData.getEmail())) {
      return true;
    }

    context.disableDefaultConstraintViolation();
    context
        .buildConstraintViolationWithTemplate("{UserAlreadyExists}")
        .addPropertyNode("email")
        .addConstraintViolation();
    return false;
  }
}
