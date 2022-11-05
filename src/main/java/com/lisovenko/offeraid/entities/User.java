package com.lisovenko.offeraid.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Table(name = "users")
@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 120)
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Size(max = 120)
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Size(max = 200)
    @Column(nullable = false)
    private String password;

    private boolean enabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Offer> offers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFavOffer> favouriteOffers = new ArrayList<>();

    @CreationTimestamp private LocalDateTime createdAt;

    @UpdateTimestamp private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {}

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = false;
    }

    public void addOffer(Offer offer) {
        offers.add(offer);
        offer.setUser(this);
    }

    public void removeOffer(Offer offer) {
        offers.remove(offer);
        offer.setUser(null);
    }

    public void addFavouriteOffer(Offer offer) {
        UserFavOffer userFavOffer = new UserFavOffer(this, offer);
        favouriteOffers.add(userFavOffer);
        offer.getFavouritedByUsers().add(userFavOffer);
    }

    public boolean hasFavouritedOffer(Offer offer) {
        for (UserFavOffer userFavOffer : favouriteOffers) {
            if (userFavOffer.getUser().equals(this)
                    && userFavOffer.getOffer().equals(offer)) {
                return true;
            }
        }
        return false;
    }

    public void removeFavouriteOffer(Offer offer) {
        for (Iterator<UserFavOffer> iterator = favouriteOffers.iterator();
             iterator.hasNext(); ) {
            UserFavOffer userFavOffer = iterator.next();

            if (userFavOffer.getUser().equals(this)
                    && userFavOffer.getOffer().equals(offer)) {
                iterator.remove();
                userFavOffer.getOffer().getFavouritedByUsers().remove(userFavOffer);
                userFavOffer.setUser(null);
                userFavOffer.setOffer(null);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        User user = (User) o;

        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "{0}(id = {1}, name = {2}, email = {3}, enabled = {4}, createdAt = {5}, updatedAt = {6})",
                getClass().getSimpleName(), id, name, email, enabled, createdAt, updatedAt);
    }
}
