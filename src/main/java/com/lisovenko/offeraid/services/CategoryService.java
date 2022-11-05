package com.lisovenko.offeraid.services;

import com.lisovenko.offeraid.entities.Category;
import com.lisovenko.offeraid.entities.DTOs.AreaDTO;
import com.lisovenko.offeraid.entities.DTOs.CategoryDTO;
import com.lisovenko.offeraid.entities.DTOs.CategoryOfferDTO;
import com.lisovenko.offeraid.entities.Offer;
import com.lisovenko.offeraid.processing.CategoryNotFoundException;
import com.lisovenko.offeraid.repositories.AreaRepository;
import com.lisovenko.offeraid.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
  private final CategoryRepository categoryRepository;
  private final AreaRepository areaRepository;

  @Transactional(readOnly = true)
  public List<CategoryDTO> getAllCategories() {
    List<Category> categories = categoryRepository.findAllById();

    return categories.stream()
            .map(
                    category -> new CategoryDTO(category))
            .toList();
  }

  @Transactional(readOnly = true)
  public List<CategoryOfferDTO> getAllCategoriesWithOfferCount(AreaDTO areaDTO) {
    List<Category> categories = categoryRepository.findAllById();
    List<Integer> areaIds = areaRepository.findAreas(areaDTO.url());

    return categories.stream()
            .map(
                    category ->
                            getCategoryOfferDTO(
                                    category, areaIds))
            .toList();
  }

  public CategoryDTO findByUrl(String url) {
    return categoryRepository
            .findByUrl(url)
            .map(CategoryDTO::new)
            .orElseThrow(CategoryNotFoundException::new);
  }

  private CategoryOfferDTO getCategoryOfferDTO(
          Category category, List<Integer> areaIds) {
    long offerSize = getOfferSize(category, areaIds);
    return new CategoryOfferDTO(
            category.getCatName(),
            category.getCatUrl(),
            offerSize);
  }

  private long getOfferSize(Category category, List<Integer> areaIds) {
    List<Offer> offers = category.getOffers();
    return offers.stream()
            .filter(offer -> offer.isActive() && areaIds.contains(offer.getArea().getId()))
            .count();
  }
}