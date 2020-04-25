package com.portgas.springbootnovice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "role")
public class Role extends AbstractAuditedEntity
{
    private static final long serialVersionUID = 3437646500010390350L;
    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "is_published")
    private Boolean isPublished;

    @Column(name = "display_sequence")
    private Integer displaySequence;

    public Role()
    {
        super();
    }

    public Role(UUID id)
    {
        super(id);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public Integer getDisplaySequence()
    {
        return displaySequence;
    }

    public void setDisplaySequence(Integer displaySequence)
    {
        this.displaySequence = displaySequence;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }
}
