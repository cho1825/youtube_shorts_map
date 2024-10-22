package com.youtube_shorts_map.domain.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "city")
public class City extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 225)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String regionCode;

    public City() {
    }

    public City(String name, String regionCode) {
        this.name = name;
        this.regionCode = regionCode;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRegionCode() {
        return regionCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(getId(), city.getId()) && Objects.equals(getRegionCode(), city.getRegionCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRegionCode());
    }
}
