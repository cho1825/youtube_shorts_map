package com.youtube_shorts_map.repository;

import com.youtube_shorts_map.domain.entity.VideoPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoPlaceRepository extends JpaRepository<VideoPlace, Long> {
}
