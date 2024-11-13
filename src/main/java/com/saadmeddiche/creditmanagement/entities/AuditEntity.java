package com.saadmeddiche.creditmanagement.entities;

import jakarta.persistence.*;
import lombok.Setter;

import java.util.Date;

@Setter
@MappedSuperclass
@EntityListeners(value = { AuditEntityListener.class })
public abstract class AuditEntity{

    private String createdBy;

    private Date createdOn;

    private String updatedBy;

    private Date updatedOn;

    @Column(name = "created_by", updatable=false)
    public String getCreatedBy() {
        return createdBy;
    }

    @Temporal(TemporalType.DATE.TIMESTAMP)
    @Column(name = "created_on", updatable=false)
    public Date getCreatedOn() {
        return createdOn;
    }

    @Column(name = "updated_by")
    public String getUpdatedBy() {
        return updatedBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on")
    public Date getUpdatedOn() {
        return updatedOn;
    }

}