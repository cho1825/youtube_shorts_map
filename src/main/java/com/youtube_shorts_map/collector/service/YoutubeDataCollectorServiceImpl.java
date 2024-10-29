package com.youtube_shorts_map.collector.service;

import com.youtube_shorts_map.collector.videoFactory.VideoInfoFactory;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class YoutubeDataCollectorServiceImpl implements YoutubeDataCollectorService{

    private final VideoRepository videoRepository;
    private final PlaceRepository placeRepository;
    private final VideoPlaceRepository videoPlaceRepository;
    private final YouTuberRepository youTuberRepository;

    @Override
    public List<Youtuber> getYoutuberList() {
        return youTuberRepository.findByDeleted("N");
    }

    @Override
    public void collectYoutubeData(String channelId) {

        //아이디로 비디어 데이터 가져와서 List 만들기
        List<Video> videos = searchVideoById(channelId);
        VideoInfoFactory factory = VideoInfoFactory.createFactory(channelId);
        //비디오 엔티티 저장
        videoRepository.saveAll(videos);
        for (Video video : videos) {
            //영상정보를 가지고 식당데이터 추출
            Place place = factory.getPlaceInfo(video);
            placeRepository.save(place);
            VideoPlace videoPlace = new VideoPlace(video, place);
            videoPlaceRepository.save(videoPlace);
        }
    }

    private List<Video> searchVideoById(String channelId) {
        //유튜브 api 연결
        return List.of();
    }


    @Override
    public void collectYoutubeDataAll(Youtuber youtuber) {

    }

}
