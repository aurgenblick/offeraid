package com.lisovenko.offeraid.entities.DTOs;

import com.lisovenko.offeraid.entities.Area;

public record AreaDTO(Integer id, String name, String url, boolean usable) {
  public AreaDTO(Area area) {
    this(area.getId(), area.getAreaName(), area.getAreaUrl(), area.isUsable());
  }
}
