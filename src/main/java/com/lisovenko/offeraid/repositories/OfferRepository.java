package com.lisovenko.offeraid.repositories;

import com.lisovenko.offeraid.entities.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends PagingAndSortingRepository<Offer, Integer> {
  @Query(
      value =
          "select o from Offer o"
              + " left join fetch o.user left join fetch o.area"
              + " where o.area.id in :areaIds and o.category.id = :categoryId and o.active = true",
      countQuery =
          "select count(o) from Offer o"
              + " left join o.user left join o.area"
              + " where o.area.id in :areaIds and o.category.id = :categoryId and o.active = true")
  Page<Offer> findAllActiveFor(List<Integer> areaIds, Integer categoryId, Pageable pageable);

  @Query(
      value =
          "select o from Offer o"
              + " left join fetch o.user left join fetch o.area"
              + " where o.user.email = :email",
      countQuery =
          "select count(o) from Offer o"
              + " left join o.user left join o.area"
              + " where o.user.email = :email")
  Page<Offer> findAllFor(String email, Pageable pageable);

  @Query(
      "select o from Offer o"
          + " join fetch o.area join fetch o.category join fetch o.user"
          + " where o.id = :offerId")
  Optional<Offer> findByIdWithAreaAndCategoryAndUser(Integer offerId);

  @Query(
      "select case when count(o) > 0 then true else false end from Offer o"
          + " left join o.user"
          + " where o.id = :offerId and o.user.email = :email")
  boolean offerBelongsTo(Integer offerId, String email);

  @Query(
      "select o from Offer o"
          + " where search_function(title, :query) = true or search_function(body, :query) = true")
  Page<Offer> findForQuery(String query, Pageable pageable);
}
