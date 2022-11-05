package com.lisovenko.offeraid.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
        name = "categories",
        indexes = {@Index(columnList = "url", unique = true)})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String catName;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String catUrl;


    private boolean usable;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Offer> offers = new ArrayList<>();

    public Category(String name) {
        this.catName = name;
    }

    public void addOffer(Offer offer) {
        offers.add(offer);
        offer.setCategory(this);
    }

    public void removeOffer(Offer offer) {
        offers.remove(offer);
        offer.setCategory(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Category category = (Category) o;

        return id != null && id.equals(category.id);
    }

    //@TODO define better hashcode
    @Override
    public int hashCode() {
        return 565489845;
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "{0}(id = {1}, name = {2}, url = {3})",
                getClass().getSimpleName(),
                id,
                catName,
                catUrl);
    }
}
