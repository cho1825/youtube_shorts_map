package com.youtube_shorts_map.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class YoutuberDto {

    private Long id;
    private String name;
    private String channelId;
    private String description;  // 유튜버 설명
    private String deleted;
    private int placeCnt;

    public YoutuberDto() {
    }

    public YoutuberDto(Long id, String name, String channelId, String description, Long placeCnt) {
        this.id = id;
        this.name = name;
        this.channelId = channelId;
        this.description = description;
        this.placeCnt = placeCnt != null ? placeCnt.intValue() : 0; // Long -> int 변환 처리
    }



    @Builder
    public YoutuberDto(Long id, String name, String channelId, String description, String deleted, int placeCnt) {
        this.id = id;
        this.name = name;
        this.channelId = channelId;
        this.description = description;
        this.deleted = deleted;
        this.placeCnt = placeCnt;
    }

}
