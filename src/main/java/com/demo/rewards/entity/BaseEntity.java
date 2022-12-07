package com.demo.rewards.entity;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
public class BaseEntity implements Serializable {
    private Boolean isActive;
    private Date createdOn;
    private Integer createdBy;
    private Date lastModifiedOn;
    private Integer lastModifiedBy;
}
