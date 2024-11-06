package com.youtube_shorts_map.domain.entity;

import com.youtube_shorts_map.dto.YoutuberDto;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.Objects;

@Entity
@Table(name = "city_youtuber")
public class CityYoutuber extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "youtuber_id", nullable = false)
    private Youtuber youtuber;

    public CityYoutuber() {
    }

    @Builder
    public CityYoutuber(City city, Youtuber youtuber) {
        this.city = city;
        this.youtuber = youtuber;
    }

    public Long getId() {
        return id;
    }

    public City getCity() {
        return city;
    }

    public Youtuber getYoutuber() {
        return youtuber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityYoutuber that = (CityYoutuber) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getCity(), that.getCity()) && Objects.equals(getYoutuber(), that.getYoutuber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCity(), getYoutuber());
    }
}
