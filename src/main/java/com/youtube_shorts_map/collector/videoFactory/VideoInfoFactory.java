package com.youtube_shorts_map.collector.videoFactory;

import com.youtube_shorts_map.domain.entity.Place;
import com.youtube_shorts_map.domain.entity.Video;

import java.util.List;

public abstract class VideoInfoFactory {

    public abstract List<Place> getPlaceInfo(Video video, String kakaoApiKey);

    public VideoInfoFactory() {
    }

    public static VideoInfoFactory createFactory(String channelId){
        switch (channelId) {
            case "맠카" :
                return new MakaVideoFactory();
            default:
                throw new IllegalArgumentException("해당 유튜버에 대한 팩토리가 없습니다: " + channelId);
        }
    }
}
