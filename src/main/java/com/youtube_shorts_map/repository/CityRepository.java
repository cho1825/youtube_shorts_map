package com.youtube_shorts_map.repository;

import com.youtube_shorts_map.domain.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    City findByNameContaining(String cityName);

    Optional<City> findByRegionCode(String regionCode);
}
