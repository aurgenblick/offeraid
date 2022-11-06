package com.lisovenko.offeraid.services;

import com.lisovenko.offeraid.entities.DTOs.OfferDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ContactService {
  private final MessageSource messages;
  private final JavaMailSender mailSender;
  private final TemplateEngine templateEngine;

  @Value("${support.email}")
  private String email;

  public void sendMessage(
      OfferDTO listing, String message, String senderEmail, String listingURI) {
    var simpleMailMessage = constructEmailMessage(listing, message, senderEmail, listingURI);
    mailSender.send(simpleMailMessage);
  }

  @SneakyThrows
  private MimeMessage constructEmailMessage(
          OfferDTO listing, String message, String senderEmail, String listingURI) {
    String subject =
        messages.getMessage(
            "contact.email.subject", new String[] {senderEmail, listing.title()}, Locale.ENGLISH);

    Context context = new Context();
    context.setVariable("listing", listing);
    context.setVariable("message", message);
    context.setVariable("senderEmail", senderEmail);
    context.setVariable("listingURI", listingURI);
    context.setVariable("subject", subject);

    String process = templateEngine.process("emails/contact", context);
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
    helper.setSubject(subject);
    helper.setText(process, true);
    helper.setFrom(email);
    helper.setReplyTo(senderEmail);
    helper.setTo(listing.user().email());
    return mimeMessage;
  }
}
