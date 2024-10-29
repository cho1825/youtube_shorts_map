package com.youtube_shorts_map.repository;

import com.youtube_shorts_map.domain.entity.Youtuber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YouTuberRepository extends JpaRepository<Youtuber, Long> {

    List<Youtuber> findByDeleted(String deleted);
}
