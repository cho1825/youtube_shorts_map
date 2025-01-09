package com.youtube_shorts_map.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;


public class PlaceDto {

    private Long id;
    private String name;
    private String roadAddress;
    private String lotAddress;
    private double latitude;
    private double longitude;
    private String youtuberNm;
    private String videoUrl;
    private String description;
    private String videoId;
    private String categoryName;
    private String categoryGroupName;




    public PlaceDto() {
    }

    @Builder
    public PlaceDto(Long id, String name, String roadAddress, String lotAddress,double latitude, double longitude, String description, String videoId) {
        this.id = id;
        this.name = name;
        this.roadAddress = roadAddress;
        this.lotAddress = lotAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.videoId = videoId;
    }

    public PlaceDto(Long id, String name, String roadAddress, String lotAddress, double latitude, double longitude, String youtuberNm, String videoUrl, String description, String videoId,String categoryGroupName, String categoryName) {
        this.id = id;
        this.name = name;
        this.roadAddress = roadAddress;
        this.lotAddress = lotAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.youtuberNm = youtuberNm;
        this.videoUrl = videoUrl;
        this.description = description;
        this.videoId = videoId;
        this.categoryName = categoryName;
        this.categoryGroupName = categoryGroupName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRoadAddress() {
        return roadAddress;
    }

    public String getLotAddress() {
        return lotAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getYoutuberNm() {
        return youtuberNm;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryGroupName() {
        return categoryGroupName;
    }
}
