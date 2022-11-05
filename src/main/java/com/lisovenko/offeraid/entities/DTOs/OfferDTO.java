package com.lisovenko.offeraid.entities.DTOs;

import com.lisovenko.offeraid.entities.Area;
import com.lisovenko.offeraid.entities.Category;
import com.lisovenko.offeraid.entities.Offer;
import com.lisovenko.offeraid.entities.User;

import java.time.LocalDateTime;

public record OfferDTO(
    Integer id,
    String title,
    String body,
    boolean active,
    UserDTO user,
    AreaDTO area,
    CategoryDTO category,
    LocalDateTime createdAt) {
  public OfferDTO(Offer offer) {
    this(
        offer.getId(),
        offer.getTitle(),
        offer.getBody(),
        offer.isActive(),
        getUserDTO(offer.getUser()),
        getAreaDTO(offer.getArea()),
        getCategoryDTO(offer.getCategory()),
        offer.getCreatedAt());
  }

  private static UserDTO getUserDTO(User user) {
    return new UserDTO(user.getId(), user.getName(), user.getEmail(), "", user.isEnabled());
  }

  public static AreaDTO getAreaDTO(Area area) {
    return new AreaDTO(area);
  }

  private static CategoryDTO getCategoryDTO(Category category) {
    return new CategoryDTO(category);
  }

}
