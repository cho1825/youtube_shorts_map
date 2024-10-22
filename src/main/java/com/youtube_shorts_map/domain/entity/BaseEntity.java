package com.youtube_shorts_map.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;


    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
