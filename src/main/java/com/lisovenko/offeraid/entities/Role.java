package com.lisovenko.offeraid.entities;


import com.lisovenko.offeraid.security.RoleAuth;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Collection;

@Table(name = "roles")
@Entity
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleAuth name;

    @ManyToMany(mappedBy = "roles")
    @Column(nullable = false)
    private Collection<User> users;

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
        Role role = (Role) o;

        return id != null && id.equals(role.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "{0}(id = {1}, name = {2}, createdAt = {3}, updatedAt = {4})",
                this.getClass().getSimpleName(), id, name, createdAt, updatedAt);
    }
}
