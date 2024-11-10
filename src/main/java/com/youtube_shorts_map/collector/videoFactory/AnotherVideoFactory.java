package com.youtube_shorts_map.collector.videoFactory;

import com.youtube_shorts_map.domain.entity.Place;
import com.youtube_shorts_map.domain.entity.Video;

import java.util.List;

public class AnotherVideoFactory extends VideoInfoFactory{
    @Override
    public List<Place> getPlaceInfo(Video video, String kakaoApiKey) {
        return null;
    }
}
