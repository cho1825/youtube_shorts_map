package com.youtube_shorts_map.collector.service;

import com.youtube_shorts_map.domain.entity.Youtuber;

import java.util.List;

public interface YoutubeDataCollectorService {

    // 수집 대상(유튜버 아이디) select
    List<Youtuber> getYoutuberList();
    // 데이터 최근 10개 수집
    void collectYoutubeData(Youtuber youtuber);
    // 데이터 전체 수집
    void collectYoutubeDataAll(Youtuber youtuber);


}
