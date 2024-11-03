package com.youtube_shorts_map.collector.scheduler;

import com.youtube_shorts_map.collector.enums.ApiFetchLimit;
import com.youtube_shorts_map.collector.service.YoutubeDataCollectorService;
import com.youtube_shorts_map.domain.entity.Video;
import com.youtube_shorts_map.domain.entity.Youtuber;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class ShortsScheduler {

    private final YoutubeDataCollectorService youtubeDataCollectorService;

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void collectShortsData() throws IOException {
        List<Youtuber> youtuberList = youtubeDataCollectorService.getYoutuberList();
        for (Youtuber youtuber : youtuberList) {
            List<Video> videosFromYoutube = youtubeDataCollectorService.getVideosFromYoutube(youtuber, ApiFetchLimit.SIZE_5);
            youtubeDataCollectorService.changeVideosToPlace(youtuber,videosFromYoutube);
        }
    }


}
