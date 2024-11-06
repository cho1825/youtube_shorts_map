package com.youtube_shorts_map.repository;

import com.youtube_shorts_map.domain.entity.City;
import com.youtube_shorts_map.domain.entity.Video;
import com.youtube_shorts_map.domain.entity.VideoPlace;
import com.youtube_shorts_map.domain.entity.Youtuber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoPlaceRepository extends JpaRepository<VideoPlace, Long> {
    boolean existsByVideo(Video video);


    List<VideoPlace> findByVideo_YoutuberAndVideo_City(Youtuber youtuber, City city);
}
