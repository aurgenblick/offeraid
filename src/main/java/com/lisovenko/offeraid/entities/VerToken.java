package com.lisovenko.offeraid.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;

@Table(name = "verification_tokens")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class VerToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @CreationTimestamp private LocalDateTime createdAt;

    @UpdateTimestamp private LocalDateTime updatedAt;

    public VerToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = LocalDateTime.now().plusHours(24L);
        //@TODO debug for the case when createdAt = null
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        VerToken that = (VerToken) o;

        return id != null && id.equals(that.id);
    }

    //@TODO define better hashcode
    @Override
    public int hashCode() {
        return 945654545;
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "{0}(id = {1}, token = {2}, expiryDate = {3}, createdAt = {4}, updatedAt = {5})",
                getClass().getSimpleName(), id, token, expiryDate, createdAt, updatedAt);
    }
}

