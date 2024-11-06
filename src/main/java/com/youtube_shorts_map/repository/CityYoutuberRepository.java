package com.youtube_shorts_map.repository;

import com.youtube_shorts_map.domain.entity.City;
import com.youtube_shorts_map.domain.entity.CityYoutuber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityYoutuberRepository extends JpaRepository<CityYoutuber, Long> {
    List<CityYoutuber> findByCity(City city);
}
