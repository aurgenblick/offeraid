package com.lisovenko.offeraid.security;

import com.lisovenko.offeraid.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(username)
                .map(
                        user ->
                                User.builder()
                                        .username(user.getEmail())
                                        .password(user.getPassword())
                                        .disabled(!user.isEnabled())
                                        .roles(
                                                user.getRoles().stream()
                                                        .map(role -> role.getRoleName().name())
                                                        .toArray(String[]::new))
                                        .build())
                .orElseThrow(
                        () -> new UsernameNotFoundException("User Not Found with username: " + username));
    }
}
