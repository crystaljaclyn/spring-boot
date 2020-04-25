package com.portgas.springbootnovice.model;


import io.ebean.Model;
import io.ebean.annotation.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public class AbstractAuditedEntity extends Model implements Serializable
{
    @Id
    public UUID id;

    @SoftDelete
    @Column(name="soft_delete", columnDefinition = "BOOLEAN DEFAULT 0")
    private Boolean softDelete = false;

    @WhoCreated
    @Column(name="created_by", nullable = false, length = 255)
    private String createdBy;

    @WhenCreated
    @Column(name="created_date", nullable = false)
    private Timestamp createdDate;

    @WhoModified
    @Column(name="updated_by", length = 255)
    private String updatedBy;

    @WhenModified
    @Column(name="updated_date")
    private Timestamp updatedDate;

    public AbstractAuditedEntity()
    {
        super();
    }

    public AbstractAuditedEntity(UUID id)
    {
        super();
        this.id = id;
    }
    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    public Boolean getSoftDelete()
    {
        return softDelete;
    }

    public void setSoftDelete(Boolean softDelete)
    {
        this.softDelete = softDelete;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate)
    {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy()
    {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedDate()
    {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate)
    {
        this.updatedDate = updatedDate;
    }
}
