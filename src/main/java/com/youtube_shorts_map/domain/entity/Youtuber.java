package com.youtube_shorts_map.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.Objects;

@Entity
@Table(name = "youtuber")
public class Youtuber extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, unique = true, length = 255)
    private String channelId;

    @Column(length = 5000)
    private String description;  // 유튜버 설명

    @Column(name = "deleted", length = 1)
    private String deleted;


    public Youtuber() {
    }

    @Builder
    public Youtuber(String name, String channelId, String description) {
        this.name = name;
        this.channelId = channelId;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getDescription() {
        return description;
    }

    public String getDeleted() {
        return deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Youtuber youtuber = (Youtuber) o;
        return Objects.equals(getId(), youtuber.getId()) && Objects.equals(getChannelId(), youtuber.getChannelId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getChannelId());
    }
}
