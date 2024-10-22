package com.youtube_shorts_map.domain.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "place")
public class Place extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 500)
    private String address;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    public Place() {
    }

    public Place(City city, String name, String address, double latitude, double longitude) {
        this.city = city;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public City getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Objects.equals(getId(), place.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
