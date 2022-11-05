package com.lisovenko.offeraid.repositories;

import com.lisovenko.offeraid.entities.UserFavOffer;
import com.lisovenko.offeraid.entities.UserFavOfferId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserFavouriteOfferRepository
    extends PagingAndSortingRepository<UserFavOffer, UserFavOfferId> {
  @Query(
      value =
          "select o from UserFavOffer o"
              + " join fetch o.user ou join fetch o.offer oo"
              + " join fetch oo.category join fetch oo.area"
              + " where ou.email = :email and oo.active = true",
      countQuery =
          "select count(o) from UserFavOffer o"
              + " left join o.user ou left join o.offer oo"
              + " where ou.email = :email and oo.active = true")
  Page<UserFavOffer> findFavouriteOffers(String email, Pageable pageable);
}
