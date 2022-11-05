package com.lisovenko.offeraid.services;

import com.lisovenko.offeraid.entities.VerToken;
import lombok.RequiredArgsConstructor;
import com.lisovenko.offeraid.entities.DTOs.UserDTO;
import com.lisovenko.offeraid.processing.TokenVerificationStatus;
import com.lisovenko.offeraid.repositories.UserRepository;
import com.lisovenko.offeraid.repositories.VerTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerTokenService {
  private final UserRepository userRepository;
  private final VerTokenRepository tokenRepository;

  @Transactional
  public String createVerTokenForUser(UserDTO userDTO) {
    String token = UUID.randomUUID().toString();

    userRepository
        .findById(userDTO.id())
        .map(
            user -> {
              var verificationToken = new VerToken(token, user);
              return tokenRepository.save(verificationToken);
            })
        .orElseThrow();

    return token;
  }

  @Transactional
  public TokenVerificationStatus validateVerToken(String token) {
    var optionalVerificationToken = tokenRepository.findByToken(token);
    if (optionalVerificationToken.isEmpty()) {
      return TokenVerificationStatus.TOKEN_INVALID;
    }

    var verificationToken = optionalVerificationToken.get();
    var user = verificationToken.getUser();

    if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
      tokenRepository.delete(verificationToken);
      return TokenVerificationStatus.TOKEN_EXPIRED;
    }

    user.setEnabled(true);
    userRepository.save(user);
    tokenRepository.delete(verificationToken);
    return TokenVerificationStatus.TOKEN_VERIFIED;
  }
}
