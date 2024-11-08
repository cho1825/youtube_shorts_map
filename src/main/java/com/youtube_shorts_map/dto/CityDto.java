package com.youtube_shorts_map.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CityDto {

    private Long id;
    private String name;
    private String regionCode;

    public CityDto() {
    }

    @Builder
    public CityDto(Long id, String name, String regionCode) {
        this.id = id;
        this.name = name;
        this.regionCode = regionCode;
    }


}
