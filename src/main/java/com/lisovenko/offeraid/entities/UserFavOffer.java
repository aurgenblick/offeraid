package com.lisovenko.offeraid.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

    @Entity
    @Table(name = "user_favourite_offer")
    @Getter
    @Setter
    @NoArgsConstructor
    public class UserFavOffer {
        @EmbeddedId
        private UserFavOfferId id;

        @ManyToOne(fetch = FetchType.LAZY)
        @MapsId("userId")
        private User user;

        @ManyToOne(fetch = FetchType.LAZY)
        @MapsId("offerId")
        private Offer offer;

        @CreationTimestamp
        private LocalDateTime createdAt;

        public UserFavOffer(User user, Offer offer) {
            this.user = user;
            this.offer = offer;
            this.id = new UserFavOfferId(user.getId(), offer.getId());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            UserFavOffer that = (UserFavOffer) o;
            return Objects.equals(user, that.user) && Objects.equals(offer, that.offer);
        }

        @Override
        public int hashCode() {
            return Objects.hash(user, offer);
        }
    }
