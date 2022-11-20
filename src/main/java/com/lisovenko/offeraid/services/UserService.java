package com.lisovenko.offeraid.services;

import com.lisovenko.offeraid.entities.DTOs.UserDTO;
import com.lisovenko.offeraid.entities.User;
import com.lisovenko.offeraid.processing.RoleNotFoundException;
import com.lisovenko.offeraid.processing.UserAlreadyExistsException;
import com.lisovenko.offeraid.processing.UserNotFoundException;
import com.lisovenko.offeraid.repositories.RoleRepository;
import com.lisovenko.offeraid.repositories.UserRepository;
import com.lisovenko.offeraid.security.RoleAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.email())) {
            throw new UserAlreadyExistsException(
                    "There is an account with that email address: %s".formatted(userDTO.email()));
        }

        var user =
                new User(userDTO.name(), userDTO.email(), passwordEncoder.encode(userDTO.password()));
                //new User(userDTO.name(), userDTO.email(), userDTO.password());
        var role = roleRepository.findByName(RoleAuth.USER);

        if (role.isEmpty()) {
            throw new RoleNotFoundException("Role not found");
        }

        user.setRoles(Set.of(role.get()));
        user = userRepository.save(user);

        return new UserDTO(
                user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.isEnabled());
    }

    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserDTO findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .map(
                        user ->
                                new UserDTO(user.getId(), user.getName(), user.getEmail(), "", user.isEnabled()))
                .orElseThrow(UserNotFoundException::new);
    }
}
