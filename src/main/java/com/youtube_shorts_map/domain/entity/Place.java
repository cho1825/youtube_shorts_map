package com.youtube_shorts_map.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;

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

    @Column(nullable = false, length = 1000)
    private String lotAddress;

    @Column(nullable = false, length = 1000)
    private String roadAddress;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = true)
    private String mapUrl;

    @Column(nullable = true)
    private String phoneNumber;

    public Place() {
    }

    @Builder
    public Place(City city, String name, String lotAddress, String roadAddress, double latitude, double longitude, String mapUrl, String phoneNumber) {
        this.city = city;
        this.name = name;
        this.lotAddress = lotAddress;
        this.roadAddress = roadAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.mapUrl = mapUrl;
        this.phoneNumber = phoneNumber;
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

    public String getLotAddress() {
        return lotAddress;
    }

    public String getRoadAddress() {
        return roadAddress;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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



    public String extractCityFromAddress(String address) {
        if (address != null && address.contains(" ")) {
            return address.split(" ")[0]; // 예: "대전광역시 서구 둔산동" -> "대전광역시"
        }
        return "전국"; // 주소 형식에 맞지 않는 경우
    }
}
