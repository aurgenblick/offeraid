package com.lisovenko.offeraid.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserDetailsServiceTest implements UserDetailsService {
  public static final String USERNAME_USER = "lisovenko.test@yandex.ru";
  public static final String USERNAME_ADMIN = "aurgenblick@gmail.com";

  private final Map<String, UserDetails> users = new HashMap<>();

  public UserDetailsServiceTest(PasswordEncoder passwordEncoder) {
    addUser(createUser(passwordEncoder));
    addUser(createAdmin(passwordEncoder));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return Optional.ofNullable(users.get(username))
        .orElseThrow(() -> new UsernameNotFoundException(username));
  }

  private void addUser(UserDetails user) {
    users.put(user.getUsername(), user);
  }

  private UserDetails createUser(PasswordEncoder passwordEncoder) {
    return User.builder()
        .username(USERNAME_USER)
        .password(passwordEncoder.encode("secret"))
        .disabled(false)
        .roles(RoleAuth.USER.toString())
        .build();
  }

  private UserDetails createAdmin(PasswordEncoder passwordEncoder) {
    return User.builder()
        .username(USERNAME_ADMIN)
        .password(passwordEncoder.encode("secret"))
        .disabled(false)
        .roles(RoleAuth.USER.toString(), RoleAuth.ADMIN.toString())
        .build();
  }
}
