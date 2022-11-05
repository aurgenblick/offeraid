package com.lisovenko.offeraid.processing;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public final class OfferNotFoundException extends RuntimeException {
  public OfferNotFoundException() {
    super();
  }

  public OfferNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public OfferNotFoundException(final String message) {
    super(message);
  }

  public OfferNotFoundException(final Throwable cause) {
    super(cause);
  }
}
