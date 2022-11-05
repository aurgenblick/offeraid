package com.lisovenko.offeraid.events;

import lombok.Getter;
import com.lisovenko.offeraid.entities.DTOs.UserDTO;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
public class UserRegisteredEvent extends ApplicationEvent {

  private final UserDTO userDTO;
  private final String confirmationURI;
  private final Locale locale;

  public UserRegisteredEvent(UserDTO userDTO, Locale locale, String confirmationURI) {
    super(userDTO);
    this.userDTO = userDTO;
    this.confirmationURI = confirmationURI;
    this.locale = locale;
  }
}
