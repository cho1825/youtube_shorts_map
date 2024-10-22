package com.youtube_shorts_map.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "video_log")
public class VisitLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(nullable = false)
    private LocalDateTime visitedAt;

    public VisitLog() {
    }


    public VisitLog(User user, Place place, LocalDateTime visitedAt) {
        this.user = user;
        this.place = place;
        this.visitedAt = visitedAt;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Place getPlace() {
        return place;
    }

    public LocalDateTime getVisitedAt() {
        return visitedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisitLog visitLog = (VisitLog) o;
        return Objects.equals(getId(), visitLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}

