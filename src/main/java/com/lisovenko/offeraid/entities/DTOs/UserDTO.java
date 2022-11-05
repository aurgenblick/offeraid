package com.lisovenko.offeraid.entities.DTOs;
import com.lisovenko.offeraid.controllers.UserRegisterForm;

public record UserDTO(Integer id, String name, String email, String password, Boolean enabled) {
  public UserDTO(UserRegisterForm userRequest) {
    this(null, userRequest.getName(), userRequest.getEmail(), userRequest.getPassword(), null);
  }
}
