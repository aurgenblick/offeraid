package com.lisovenko.offeraid.entities.DTOs;

import java.time.LocalDateTime;

public record FavOfferDTO(OfferDTO offer, LocalDateTime createdAt) {}
