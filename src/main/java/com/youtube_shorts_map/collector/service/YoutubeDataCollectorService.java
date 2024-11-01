package com.youtube_shorts_map.collector.service;

import com.youtube_shorts_map.collector.enums.ApiFetchLimit;
import com.youtube_shorts_map.domain.entity.Video;
import com.youtube_shorts_map.domain.entity.Youtuber;

import java.io.IOException;
import java.util.List;

public interface YoutubeDataCollectorService {

    // 수집 대상(유튜버 아이디) select
    List<Youtuber> getYoutuberList();

    List<Video> getVideosFromYoutube(Youtuber youtuber, ApiFetchLimit apiFetchLimit) throws IOException;

    void changeVideosToPlace(Youtuber youtuber, List<Video> videos);


}
