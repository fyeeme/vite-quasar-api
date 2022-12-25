package com.fyeeme.quasar.base.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;

@SuppressWarnings("serial")
@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity<T> extends BaseEntity {

    @CreatedBy
    protected T createdBy;
    @LastModifiedBy
    protected T modifiedBy;

    @CreatedDate
    protected Instant createdDate;
    @LastModifiedDate
    protected Instant modifiedDate;
}
