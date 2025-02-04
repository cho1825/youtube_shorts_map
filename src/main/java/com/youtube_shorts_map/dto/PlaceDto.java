package com.youtube_shorts_map.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PlaceDto {

    private Long id;
    private String name;
    private String roadAddress;
    private String lotAddress;
    private double latitude;
    private double longitude;
    private String youtuberNm;
    private String videoTitle;
    private String videoUrl;
    private String description;
    private String videoId;
    private String categoryName;
    private String categoryGroupName;
    private String phoneNumber;
    private LocalDateTime publishedAt;


    public PlaceDto() {
    }

    @Builder
    public PlaceDto(Long id
            , String name
            , String roadAddress
            , String lotAddress
            , double latitude
            , double longitude
            , String description
            , String videoId
            , String phoneNumber
            , String title
            , LocalDateTime publishedAt
    ) {
        this.id = id;
        this.name = name;
        this.roadAddress = roadAddress;
        this.lotAddress = lotAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.videoId = videoId;
        this.phoneNumber = phoneNumber;
        this.videoTitle = title;
        this.publishedAt = publishedAt;
    }

    public PlaceDto(Long id
            , String name
            , String roadAddress
            , String lotAddress
            , double latitude
            , double longitude
            , String youtuberNm
            , String videoUrl
            , String description
            , String videoId
            , String categoryGroupName
            , String categoryName
            , String phoneNumber
            , String title
            , LocalDateTime publishedAt
    ) {
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
        this.phoneNumber = phoneNumber;
        this.videoTitle = title;
        this.publishedAt = publishedAt;

    }

    public String getCategoryName() {
        int i = categoryName.lastIndexOf(">");
        String substring = categoryName.substring(i + 2);
        return substring;
    }

}
