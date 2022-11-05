package com.lisovenko.offeraid.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class LoginController {
  @GetMapping("/login")
  public String login(
      HttpServletRequest request,
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestParam(required = false) String error) {
    if (error != null) {
      logError(request);
    }

    if (userDetails == null) {
      return "auth/login";
    } else {
      return "redirect:/";
    }
  }

  private void logError(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      AuthenticationException ex =
          (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
      if (ex != null) {
        //@TODO get better logging
        //System.out.println(ex.getLocalizedMessage());
        log.warn(ex.getMessage());
      }
    }
  }
}
