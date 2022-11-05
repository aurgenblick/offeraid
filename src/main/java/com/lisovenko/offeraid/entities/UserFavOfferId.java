package com.lisovenko.offeraid.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFavOfferId implements Serializable {
    static final long serialVersionUID = -1192120145739583721L;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "offer_id")
    private Integer offerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserFavOfferId that = (UserFavOfferId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(offerId, that.offerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, offerId);
    }
}
