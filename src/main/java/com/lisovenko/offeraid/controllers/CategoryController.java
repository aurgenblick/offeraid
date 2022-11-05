package com.lisovenko.offeraid.controllers;

import com.lisovenko.offeraid.entities.DTOs.AreaDTO;
import com.lisovenko.offeraid.entities.DTOs.CategoryOfferDTO;
import com.lisovenko.offeraid.services.AreaService;
import com.lisovenko.offeraid.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.lisovenko.offeraid.config.SessionConfig.CURRENT_AREA;

@Controller
@RequiredArgsConstructor
public class CategoryController {
  private final AreaService areaService;
  private final CategoryService categoryService;

  @GetMapping("/{areaUrl}/categories")
  public String index(@PathVariable String areaUrl, Model model, HttpSession session) {
    AreaDTO areaDTO = areaService.findByUrl(areaUrl);
    session.setAttribute(CURRENT_AREA, areaDTO);

    List<CategoryOfferDTO> categories =
        categoryService.getAllCategoriesWithOfferCount(areaDTO);
    model.addAttribute("categories", categories);
    return "categories";
  }
}
