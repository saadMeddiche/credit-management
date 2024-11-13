package com.saadmeddiche.creditmanagement.entities;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.util.Date;

public class AuditEntityListener {

    @PrePersist
    public void prePersist(AuditEntity item) {
        if (item != null) {
            item.setCreatedOn(new Date());
        }
    }

    @PreUpdate
    public void preUpdate(AuditEntity item) {
        if (item != null) {
            item.setUpdatedOn(new Date());
        }
    }

}
