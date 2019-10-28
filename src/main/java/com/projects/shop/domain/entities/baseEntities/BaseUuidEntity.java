package com.projects.shop.domain.entities.baseEntities;

import com.projects.shop.domain.api.Identifiable;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

/**
 * <p>
 * Use space optimized BINARY(16) instead of CHAR/VARCHAR(36) database format
 * </p>
 * <p>
 * Use private setter to prevent immutability
 * </p>
 */
@MappedSuperclass
public abstract class BaseUuidEntity implements Identifiable<UUID> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(unique = true, nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    @Access(AccessType.PROPERTY)
    private UUID id;


    public UUID getId() {
        return id;
    }

    private void setId(UUID id) {
        this.id = id;
    }
}
