package com.youtube_shorts_map.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.Objects;

@Entity
@Table(name = "video_place")
public class VideoPlace extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id" , nullable = false)
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    public VideoPlace() {
    }

    @Builder
    public VideoPlace(Video video, Place plcae) {
        this.video = video;
        this.place = plcae;
    }

    public Long getId() {
        return id;
    }

    public Video getVideo() {
        return video;
    }

    public Place getPlace() {
        return place;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoPlace that = (VideoPlace) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
