package com.youtube_shorts_map.collector.controller;

import com.youtube_shorts_map.collector.enums.ApiFetchLimit;
import com.youtube_shorts_map.collector.service.YoutubeDataCollectorService;
import com.youtube_shorts_map.domain.entity.Video;
import com.youtube_shorts_map.domain.entity.Youtuber;
import com.youtube_shorts_map.repository.VideoRepository;
import com.youtube_shorts_map.repository.YouTuberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/youtubers")
@RequiredArgsConstructor
public class YoutubeApiCollectController {

    private final YoutubeDataCollectorService youtubeDataCollectorService;
    private final YouTuberRepository youTuberRepository;
    private final VideoRepository videoRepository;

    @GetMapping("/videos")
    public void saveMakaaVideo() throws IOException {

        // Pageable 설정 (0번 페이지에서 20개 가져오기)
        Pageable pageable = PageRequest.of(0, 20);

        Optional<Youtuber> makka = youTuberRepository.findById(1L);
//        List<Video> videos = videoRepository.findAllByYoutuber(byId.get(),pageable);
//        List<Video> videos = videoRepository.findAllByYoutuber(byId.get());
        List<Video> videos = youtubeDataCollectorService.getVideosFromYoutube(makka.get(), ApiFetchLimit.ALL);
        youtubeDataCollectorService.changeVideosToPlace(makka.get(), videos);

    }

}
