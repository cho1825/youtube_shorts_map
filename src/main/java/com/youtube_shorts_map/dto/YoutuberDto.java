package com.youtube_shorts_map.dto;

import lombok.Builder;

public class YoutuberDto {

    private Long id;
    private String name;
    private String channelId;
    private String description;  // 유튜버 설명
    private String deleted;

    public YoutuberDto() {
    }

    @Builder
    public YoutuberDto(Long id, String name, String channelId, String description, String deleted) {
        this.id = id;
        this.name = name;
        this.channelId = channelId;
        this.description = description;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getDescription() {
        return description;
    }

    public String getDeleted() {
        return deleted;
    }
}
