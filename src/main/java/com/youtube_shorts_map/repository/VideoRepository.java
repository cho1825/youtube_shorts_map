package com.youtube_shorts_map.repository;

import com.youtube_shorts_map.domain.entity.Video;
import com.youtube_shorts_map.domain.entity.Youtuber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findAllByYoutuber(Youtuber youtuber);
}
