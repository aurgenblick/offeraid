package com.lisovenko.offeraid.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.text.MessageFormat;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
        name = "areas",
        indexes = {@Index(columnList = "url", unique = true)})
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String areaName;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String areaUrl;

    private boolean usable;

    @CreationTimestamp private LocalDateTime createdAt;

    @UpdateTimestamp private LocalDateTime updatedAt;


    public Area(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Area area = (Area) o;

        return id != null && id.equals(area.id);
    }

    //@TODO define better hashcode
    @Override
    public int hashCode() {
        return 1121645698;
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "{0}(id = {1}, name = {2}, url = {3}, createdAt = {4}, updatedAt = {5}",
                getClass().getSimpleName(),
                id,
                areaName,
                areaUrl,
                createdAt,
                updatedAt);
    }
}
