package com.lisovenko.offeraid.processing;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserNotExistValidator.class)
public @interface UserNotExist {
  String message() default "{UserAlreadyExists}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
