package com.youtube_shorts_map.collector.service;

public interface YoutubeDataCollectorService {

    // 수동 데이터 수집 트리거 메서드 정의
    void collectYoutubeData(String youtuberId);

    // 스케줄링을 통한 주기적인 데이터 수집 메서드 정의
    void scheduledYoutubeDataCollection();

}
