package com.lisovenko.offeraid.entities.DTOs;

import com.lisovenko.offeraid.entities.Category;

public record CategoryDTO(Integer id, String name, String url, boolean usable) {
  public CategoryDTO(Category category) {
    this(
        category.getId(),
        category.getCatName(),
        category.getCatUrl(),
        category.isUsable());
  }
}
