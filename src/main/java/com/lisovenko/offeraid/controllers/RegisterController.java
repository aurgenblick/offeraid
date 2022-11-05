package com.lisovenko.offeraid.controllers;

import com.lisovenko.offeraid.entities.DTOs.UserDTO;
import com.lisovenko.offeraid.events.UserRegisteredEvent;
import com.lisovenko.offeraid.services.UserService;
import com.lisovenko.offeraid.services.VerTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RegisterController {
  private final UserService userService;
  private final VerTokenService tokenService;
  private final ApplicationEventPublisher eventPublisher;

  @GetMapping("/register")
  public String index(@AuthenticationPrincipal UserDetails userDetails, Model model) {
    if (userDetails == null) {
      model.addAttribute("registerUserForm", new RegisterRequestController());
      return "auth/register";
    } else {
      return "redirect:/";
    }
  }

  @PostMapping("/register")
  public String store(
      @Valid @ModelAttribute("registerUserForm") UserRegisterForm registerForm,
      BindingResult bindingResult,
      Model model,
      HttpServletRequest request,
      RedirectAttributes redirectAttributes,
      UriComponentsBuilder uriComponentsBuilder) {

    if (bindingResult.hasErrors()) {
      model.addAttribute("registerUserForm", registerForm);
      return "auth/register";
    }

    UserDTO registeredUser = userService.createUser(new UserDTO(registerForm));
    String token = tokenService.createVerTokenForUser(registeredUser);
    String confirmationURI = getConfirmationURI(uriComponentsBuilder, token);

    eventPublisher.publishEvent(
            new UserRegisteredEvent(registeredUser, request.getLocale(), confirmationURI));
    redirectAttributes.addFlashAttribute("confirmationURI", confirmationURI);

    redirectAttributes.addFlashAttribute("registrationSuccess", true);
    return "redirect:/login";
  }

  private String getConfirmationURI(UriComponentsBuilder uriComponentsBuilder, String token) {
    return uriComponentsBuilder
            .uriComponents(
                    MvcUriComponentsBuilder.fromMethodName(
                                    RegisterController.class, "confirmEmail", token, new Object())
                            .build())
            .toUriString();
  }

  @GetMapping("/register/confirm")
  public String confirmEmail(@RequestParam String token, RedirectAttributes redirectAttributes) {
    var tokenVerificationStatus = tokenService.validateVerToken(token);

    switch (tokenVerificationStatus) {
      case TOKEN_VERIFIED -> {
        redirectAttributes.addFlashAttribute("confirmationSuccess", true);
        return "redirect:/login";
      }
      case TOKEN_EXPIRED -> {
        redirectAttributes.addFlashAttribute("tokenExpired", true);
        return "redirect:/login";
      }
      default -> {
        redirectAttributes.addFlashAttribute("verificationInProgress", true);
        return "redirect:/login";
      }
    }
  }
}
