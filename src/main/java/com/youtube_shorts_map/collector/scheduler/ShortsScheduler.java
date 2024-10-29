package com.youtube_shorts_map.collector.scheduler;

import com.youtube_shorts_map.collector.service.YoutubeDataCollectorService;
import com.youtube_shorts_map.domain.entity.Youtuber;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@RequiredArgsConstructor
public class ShortsScheduler {

    private final YoutubeDataCollectorService youtubeDataCollectorService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void collectShortsData(){
        List<Youtuber> youtuberList = youtubeDataCollectorService.getYoutuberList();
        for (Youtuber youtuber : youtuberList) {
            youtubeDataCollectorService.collectYoutubeData(youtuber.getChannelId());
        }
    }


}
