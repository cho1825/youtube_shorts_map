package com.youtube_shorts_map.repository;

import com.youtube_shorts_map.domain.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface VideoRepository extends JpaRepository<Video, Long> {

}
