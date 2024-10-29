package com.youtube_shorts_map.collector.videoFactory;

import com.youtube_shorts_map.domain.entity.Place;
import com.youtube_shorts_map.domain.entity.Video;
import org.springframework.context.annotation.Bean;

public abstract class VideoInfoFactory {

    public abstract Place getPlaceInfo(Video video);

    public VideoInfoFactory() {
    }

    public static VideoInfoFactory createFactory(String channelId){
        switch (channelId) {
            case "makka" :
                return new MakaVideoFactory();
            default:
                throw new IllegalArgumentException("해당 유튜버에 대한 팩토리가 없습니다: " + channelId);
        }
    }
}
