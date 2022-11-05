package com.lisovenko.offeraid.services;

import com.lisovenko.offeraid.controllers.OfferForm;
import com.lisovenko.offeraid.entities.DTOs.AreaDTO;
import com.lisovenko.offeraid.entities.DTOs.CategoryDTO;
import com.lisovenko.offeraid.entities.DTOs.FavOfferDTO;
import com.lisovenko.offeraid.entities.DTOs.OfferDTO;
import com.lisovenko.offeraid.entities.Offer;
import com.lisovenko.offeraid.entities.User;
import com.lisovenko.offeraid.entities.UserFavOffer;
import com.lisovenko.offeraid.processing.AreaNotFoundException;
import com.lisovenko.offeraid.processing.CategoryNotFoundException;
import com.lisovenko.offeraid.processing.OfferNotFoundException;
import com.lisovenko.offeraid.processing.UserNotFoundException;
import com.lisovenko.offeraid.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OfferService {
  public final UserRepository userRepository;
  public final AreaRepository areaRepository;
  public final CategoryRepository categoryRepository;
  public final OfferRepository offerRepository;
  public final UserFavouriteOfferRepository userFavouriteOfferRepository;

  public Page<OfferDTO> getByAreaAndCategory(
      AreaDTO areaDTO, CategoryDTO categoryDTO, Pageable pageable) {
    List<Integer> areaId = areaRepository.findAreas(areaDTO.url());
    Page<Offer> offers =
        offerRepository.findAllActiveFor(areaId, categoryDTO.id(), pageable);

    return getOfferDTOPage(offers);
  }

  public Page<OfferDTO> getByUserEmail(String email, Pageable pageable) {
    Page<Offer> offers = offerRepository.findAllFor(email, pageable);

    return getOfferDTOPage(offers);
  }

  public OfferDTO getById(Integer offerId) {
    return offerRepository
        .findByIdWithAreaAndCategoryAndUser(offerId)
        .map(OfferDTO::new)
        .orElseThrow(OfferNotFoundException::new);
  }

  public boolean belongsTo(Integer offerId, String email) {
    return offerRepository.offerBelongsTo(offerId, email);
  }

  @Transactional
  public void storeFavouriteOffer(Integer offerId, String email) {
    Offer offer =
        offerRepository.findById(offerId).orElseThrow(OfferNotFoundException::new);
    User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    user.addFavouriteOffer(offer);
  }

  public boolean hasUserFavourited(Integer offerId, String email) {
    Offer offer =
        offerRepository.findById(offerId).orElseThrow(OfferNotFoundException::new);
    User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    return user.hasFavouritedOffer(offer);
  }

  @Transactional
  public void deleteFavouriteOffer(Integer offerId, String email) {
    Offer offer =
        offerRepository.findById(offerId).orElseThrow(OfferNotFoundException::new);
    User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    user.removeFavouriteOffer(offer);
  }

  public Page<FavOfferDTO> getFavouriteOffers(String email, Pageable pageable) {
    Page<UserFavOffer> offers =
        userFavouriteOfferRepository.findFavouriteOffers(email, pageable);

    List<FavOfferDTO> favOfferDTOS =
        offers.stream()
            .map(
                (UserFavOffer userFavouriteOffer) ->
                    new FavOfferDTO(
                        new OfferDTO(userFavouriteOffer.getOffer()),
                        userFavouriteOffer.getCreatedAt()))
            .toList();

    return new PageImpl<>(favOfferDTOS, offers.getPageable(), offers.getTotalElements());
  }

  @Transactional
  public OfferDTO createOffer(OfferForm offerForm) {
    var user =
        userRepository
            .findByEmail(offerForm.getUserEmail())
            .orElseThrow(UserNotFoundException::new);
    var area =
        areaRepository.findById(offerForm.getAreaId()).orElseThrow(AreaNotFoundException::new);
    var category =
        categoryRepository
            .findById(offerForm.getCategoryId())
            .orElseThrow(CategoryNotFoundException::new);

    Assert.state(
        user.isEnabled(), "createOffer user(id=%d) must be enabled".formatted(user.getId()));
    Assert.state(
        area.isUsable(), "createOffer area(id=%d) must be usable".formatted(area.getId()));
    Assert.state(
        category.isUsable(),
        "createOffer category(id=%d) must be usable".formatted(category.getId()));

    var offer = new Offer();

    offer.setTitle(offerForm.getTitle());
    offer.setBody(offerForm.getBody());
    offer.setUser(user);
    offer.setCategory(category);
    offer.setArea(area);

    offer = offerRepository.save(offer);

    return new OfferDTO(offer);
  }

  @Transactional
  public OfferDTO updateOffer(OfferForm offerForm) {
    var offer =
        offerRepository.findById(offerForm.getId()).orElseThrow(OfferNotFoundException::new);

    offer.setTitle(offerForm.getTitle());
    offer.setBody(offerForm.getBody());

    var area =
        areaRepository.findById(offerForm.getAreaId()).orElseThrow(AreaNotFoundException::new);
    Assert.state(
        area.isUsable(), "createOffer area(id=%d) must be usable".formatted(area.getId()));
    offer.setArea(area);

    if (!offer.isActive()) {
      var category =
          categoryRepository
              .findById(offerForm.getCategoryId())
              .orElseThrow(CategoryNotFoundException::new);
      Assert.state(
          category.isUsable(),
          "createOffer category(id=%d) must be usable".formatted(category.getId()));
      offer.setCategory(category);
    }

    offer = offerRepository.save(offer);

    return new OfferDTO(offer);
  }

  @Transactional
  public void delete(Integer offerId) {
    offerRepository.deleteById(offerId);
  }

  public Page<OfferDTO> findByQuery(String query, Pageable pageable) {
    Page<Offer> offers = offerRepository.findForQuery(query, pageable);
    return getOfferDTOPage(offers);
  }

  private Page<OfferDTO> getOfferDTOPage(Page<Offer> offers) {
    List<OfferDTO> offerDTOS = offers.stream().map(OfferDTO::new).toList();
    return new PageImpl<>(offerDTOS, offers.getPageable(), offers.getTotalElements());
  }
}
