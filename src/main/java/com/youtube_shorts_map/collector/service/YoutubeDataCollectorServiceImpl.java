package com.youtube_shorts_map.collector.service;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.youtube_shorts_map.collector.enums.ApiFetchLimit;
import com.youtube_shorts_map.collector.videoFactory.VideoInfoFactory;
import com.youtube_shorts_map.domain.entity.Place;
import com.youtube_shorts_map.domain.entity.Video;
import com.youtube_shorts_map.domain.entity.VideoPlace;
import com.youtube_shorts_map.domain.entity.Youtuber;
import com.youtube_shorts_map.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class YoutubeDataCollectorServiceImpl implements YoutubeDataCollectorService{

    private final VideoRepository videoRepository;
    private final PlaceRepository placeRepository;
    private final VideoPlaceRepository videoPlaceRepository;
    private final YouTuberRepository youTuberRepository;
    private final CityRepository cityRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${youtube.api.key}")
    private String apiKey;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    @Override
    public List<Youtuber> getYoutuberList() {
        return youTuberRepository.findByDeleted("N");
    }

    @Override
    @Transactional
    public List<Video> getVideosFromYoutube(Youtuber youtuber, ApiFetchLimit apiFetchLimit) throws IOException {

        //아이디로 비디어 데이터 가져와서 List 만들기
        List<Video> videos = searchVideoById(youtuber, apiFetchLimit);
        List<String> videoIds = new ArrayList<>();
        for (Video video : videos) {
            String videoId = video.getVideoId();
            if (!redisTemplate.hasKey(videoId)) {
                redisTemplate.opsForValue().set(videoId, videoId, 24, TimeUnit.HOURS); // 24시간 저장
                if (!videoRepository.existsByVideoId(videoId)) {
                    videoRepository.save(video);
                    log.info("New video saved: {}", videoId);
                    videoIds.add(videoId);  // 저장된 비디오의 ID를 추가
                }else {
                    log.info("Video already exists: {}", videoId);
                }
            }else {
                log.info("Video already exists in Redis: {}", videoId);
            }
        }

        return videoRepository.findAllByVideoIdIn(videoIds);

    }

    @Override
    public void changeVideosToPlace(Youtuber youtuber, List<Video> videos) {
        VideoInfoFactory factory = VideoInfoFactory.createFactory(youtuber.getChannelId());
        for (Video video : videos) {
            try {
                // 영상 정보로부터 식당 데이터 추출
                List<Place> places = factory.getPlaceInfo(video, kakaoApiKey);
                if (places != null) {
                    for (Place place : places) {
                        try {
                            String cityNm = place.extractCityFromAddress(place.getRoadAddress());

                            Place savePlace = Place.builder()
                                    .city(cityRepository.findByNameContaining(cityNm))
                                    .name(place.getName())
                                    .lotAddress(place.getLotAddress())
                                    .roadAddress(place.getRoadAddress())
                                    .latitude(place.getLatitude())
                                    .longitude(place.getLongitude())
                                    .mapUrl(place.getMapUrl())
                                    .phoneNumber(place.getPhoneNumber())
                                    .build();

                            // 중복되지 않으면 저장
                            if (!placeRepository.existsByRoadAddress(place.getRoadAddress())) {
                                placeRepository.save(savePlace);
                            }

                            if (!videoPlaceRepository.existsByVideo(video)) {
                                VideoPlace videoPlace = VideoPlace.builder()
                                        .plcae(savePlace)
                                        .video(video)
                                        .build();
                                videoPlaceRepository.save(videoPlace);
                            }
                        } catch (Exception e) {
                            // 각 Place 처리 중 예외 발생 시 로그 출력 후 다음 Place로 넘어감
                            System.err.println("Failed to process place: " + place.getName());
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                // 비디오 정보 추출 실패 시 로그 출력 후 다음 비디오로 넘어감
                System.err.println("Failed to process video ID: " + video.getVideoId());
                e.printStackTrace();
            }
        }
    }


    public List<Video> searchVideoById(Youtuber youtuber, ApiFetchLimit apiFetchLimit) throws IOException {
        String youtuberNm = youtuber.getChannelId();
        JsonFactory jsonFactory = new JacksonFactory();
        YouTube youtube = new YouTube.Builder(
                new com.google.api.client.http.javanet.NetHttpTransport(),
                jsonFactory,
                request -> {
                }).build();

        List<Video> videos = new ArrayList<>();
        // YouTube API 요청 설정
        YouTube.Search.List search = youtube.search().list(Collections.singletonList("id,snippet"));
        search.setQ(youtuberNm);
        search.setKey(apiKey);
        search.setOrder("date");

        SearchListResponse response = search.execute();
        List<SearchResult> searchResults = response.getItems();
        if (searchResults != null && searchResults.size() > 0) {
            for (SearchResult searchResult : searchResults) {
                if (searchResult.getSnippet().getChannelTitle().equals(youtuberNm)) {
                    String channelId = searchResult.getSnippet().getChannelId();

                    YouTube.Search.List videoSearch = youtube.search().list(Collections.singletonList("id,snippet"));
                    videoSearch.setKey(apiKey);
                    videoSearch.setChannelId(channelId);  // Filter by channel ID
                    videoSearch.setType(Collections.singletonList("video"));  // 동영상 검색

                    videoSearch.setOrder("date");  // Get the most recent videos
                    int anInt = apiFetchLimit.toInt();
                    if (anInt > 10000){
                        videoSearch.setMaxResults(50L);  // 한 번에 최대 50개씩

                        String nextPageToken = null;
                        int totalFetched = 0;
                        while (totalFetched < 400) {
                            videoSearch.setPageToken(nextPageToken);  // 페이지 토큰 설정

                            SearchListResponse videoSearchResponse = videoSearch.execute();
                            List<SearchResult> videoSearchResults = videoSearchResponse.getItems();

                            if (videoSearchResults == null || videoSearchResults.isEmpty()) {
                                break;
                            }

                            buildAndAddVideo(videos, videoSearchResults, youtuber);

                            totalFetched += videoSearchResults.size();
                            nextPageToken = videoSearchResponse.getNextPageToken();  // 다음 페이지 토큰

                            if (nextPageToken == null) {
                                break;  // 더 이상 페이지가 없으면 종료
                            }
                        }
                    }else {
                        videoSearch.setMaxResults(Long.valueOf(anInt));
                        SearchListResponse videoSearchResponse = videoSearch.execute();
                        List<SearchResult> videoSearchResults = videoSearchResponse.getItems();

                        if (videoSearchResults != null && !videoSearchResults.isEmpty()) {
                            buildAndAddVideo(videos, videoSearchResults,youtuber);
                        }
                    }
                    break;
                }
            }
        }

        return videos;
    }

    private void buildAndAddVideo(List<Video> videos, List<SearchResult> videoSearchResults, Youtuber youtuber) {
        for (SearchResult videoSearchResult : videoSearchResults) {

            Video video = Video.builder()
                    .videoId(videoSearchResult.getId().getVideoId())
                    .title(videoSearchResult.getSnippet().getTitle())
                    .description(videoSearchResult.getSnippet().getDescription())
                    .youtubeUrl("https://www.youtube.com/watch?v=" + videoSearchResult.getId().getVideoId())
                    .publishedAt(LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(videoSearchResult.getSnippet().getPublishedAt().getValue()),
                            ZoneId.systemDefault()))
                    .youtuber(youtuber)
                    .build();

            videos.add(video);

        }
    }

}
