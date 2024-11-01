package com.youtube_shorts_map.repository;

import com.youtube_shorts_map.domain.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

    City findByNameContaining(String cityName);
}
