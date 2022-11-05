package com.lisovenko.offeraid.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "offers")
@Entity
@SQLDelete(sql = "UPDATE offers SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFavOffer> favouritedByUsers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Area area;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    private String title;

    private String body;

    private boolean active = Boolean.FALSE;

    private boolean deleted = Boolean.FALSE;

    @CreationTimestamp private LocalDateTime createdAt;
    @UpdateTimestamp private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Offer offer = (Offer) o;

        return id != null && id.equals(offer.id);
    }

    //@TODO define better hashcode
    @Override
    public int hashCode() {
        return 874564564;
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "{0}(id = {1}, title = {2}, body = {3}, active = {4}, deleted = {5},"
                        + " createdAt = {6}, updatedAt = {7})",
                getClass().getSimpleName(), id, title, body, active, deleted, createdAt, updatedAt);
    }
}
