package com.youtube_shorts_map.collector.service;

import com.youtube_shorts_map.domain.entity.Place;
import com.youtube_shorts_map.domain.entity.Video;
import com.youtube_shorts_map.domain.entity.VideoPlace;
import com.youtube_shorts_map.domain.entity.Youtuber;
import com.youtube_shorts_map.repository.PlaceRepository;
import com.youtube_shorts_map.repository.VideoPlaceRepository;
import com.youtube_shorts_map.repository.VideoRepository;
import com.youtube_shorts_map.repository.YouTuberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class YoutubeDataCollectorServiceImpl implements YoutubeDataCollectorService{

    private final VideoRepository videoRepository;
    private final PlaceRepository placeRepository;
    private final VideoPlaceRepository videoPlaceRepository;

    @Override
    public List<Youtuber> getYoutuberList() {
        return List.of();
    }

    @Override
    public void collectYoutubeData(Youtuber youtuberId) {

        //아이디로 검색하는 메서드
        Video video1 = new Video();
        Video video2 = new Video();
        Video video3 = new Video();
        Video video4 = new Video();

        List<Video> videos = new ArrayList<>();
        videos.add(video1);
        videos.add(video2);
        videos.add(video3);
        videos.add(video4);
        //비디오 엔티티 저장
        videoRepository.saveAll(videos);
        for (Video video : videos) {
            //영상정보를 가지고 식당데이터 추출
            Place place = getPlace(video);
            placeRepository.save(place);

            VideoPlace videoPlace = new VideoPlace(video, place);
            videoPlaceRepository.save(videoPlace);
        }
    }


    @Override
    public void collectYoutubeDataAll(Youtuber youtuber) {

    }


    //영상정보를 가지고 식당데이터 추출
    private Place getPlace(Video video) {
        Place place = new Place();

        return place;
    }

}
