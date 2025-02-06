package com.youtube_shorts_map.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "video")
@Getter
public class Video extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String videoId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 5000)
    private String description;

    @Column(nullable = false, length = 255)
    private String youtubeUrl;

    @Column(nullable = false)
    private LocalDateTime publishedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "youtuber_id", nullable = true)
    private Youtuber youtuber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = true)
    private City city;

    public Video() {
    }

    @Builder
    public Video(String videoId, String title, String description, String youtubeUrl, LocalDateTime publishedAt, Youtuber youtuber, City city) {
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.youtubeUrl = youtubeUrl;
        this.publishedAt = publishedAt;
        this.youtuber = youtuber;
        this.city = city;
    }

    public void updateVideoCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Video video = (Video) o;
        return Objects.equals(getVideoId(), video.getVideoId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getVideoId());
    }
}
