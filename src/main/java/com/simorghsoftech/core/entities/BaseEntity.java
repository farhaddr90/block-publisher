package com.simorghsoftech.core.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity extends PanacheEntityBase implements Serializable {

    @Column(name = "creation_date")
    public LocalDateTime creationDate;

    @Column(name = "last_modified_date")
    public LocalDateTime lastModifiedDate;

    @PrePersist
    private void prePersist(){
        creationDate = LocalDateTime.now();
        lastModifiedDate = creationDate;
    }

    @PreUpdate
    private void preUpdate(){
        lastModifiedDate = LocalDateTime.now();
    }
}