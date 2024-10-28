package com.youtube_shorts_map.repository;

import com.youtube_shorts_map.domain.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
