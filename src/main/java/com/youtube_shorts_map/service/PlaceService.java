package com.youtube_shorts_map.service;

import com.youtube_shorts_map.domain.entity.City;
import com.youtube_shorts_map.domain.entity.VideoPlace;
import com.youtube_shorts_map.domain.entity.Youtuber;
import com.youtube_shorts_map.dto.PlaceDto;
import com.youtube_shorts_map.repository.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final CityRepository cityRepository;
    private final YouTuberRepository youTuberRepository;
    private final VideoRepository videoRepository;
    private final VideoPlaceRepository videoPlaceRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<PlaceDto> getMakersBySpringJpa(String regionCode, String youtuberNm) {


        // City 조회
        City city = cityRepository.findByRegionCode(regionCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid region code"));

        // Youtuber 조회
        Youtuber youtuber = youTuberRepository.findByName(youtuberNm)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Youtuber name"));

        // VideoPlace 조회를 통해 관련된 Place 엔티티 가져오기
        List<VideoPlace> videoPlaces = videoPlaceRepository.findByVideo_YoutuberAndVideo_City(youtuber, city);


        // Place DTO 변환 후 반환
        return videoPlaces.stream()
                .map(videoPlace -> PlaceDto.builder()
                        .id( videoPlace.getPlace().getId())
                        .name( videoPlace.getPlace().getName())
                        .roadAddress( videoPlace.getPlace().getRoadAddress())
                        .lotAddress( videoPlace.getPlace().getLotAddress())
                        .latitude( videoPlace.getPlace().getLatitude())
                        .longitude( videoPlace.getPlace().getLongitude())
                        .build())
                .collect(Collectors.toList());
    }

    @CircuitBreaker(name = "placeCircuitBreaker", fallbackMethod = "getMakersByJPQLFallback")
    @Cacheable(value = "placeCache", key = "#regionCode + '_' + #youtuberNm")
    public List<PlaceDto> getMakersByJPQL(String regionCode, String youtuberNm, String category){


        if ("restaurant".equals(category)) {
            category = "음식점";
        }else if ("cafe".equals(category)){
            category = "카페";
        }else {
            category = null;
        }


        // JPQL 쿼리 작성

        String jpql = "SELECT new com.youtube_shorts_map.dto.PlaceDto(p.id, p.name, p.roadAddress, p.lotAddress,p.latitude, p.longitude, y.name, v.youtubeUrl, v.description, v.videoId,p.categoryGroupName,p.categoryName,p.phoneNumber) " +
                "from VideoPlace vp " +
                "Join vp.video v " +
                "join v.youtuber y " +
                "join v.city c " +
                "join vp.place p " +
                "where y.name = :youtuberNm and c.regionCode = :regionCode " +
                (category != null ? "AND p.categoryGroupName = :categoryGroupName " : "");

        TypedQuery<PlaceDto> query = entityManager.createQuery(jpql, PlaceDto.class);
        query.setParameter("regionCode", regionCode);
        query.setParameter("youtuberNm", youtuberNm);
        if (category != null) {
            query.setParameter("categoryGroupName", category);
        }

        // 결과 조회 및 반환
        return query.getResultList();
    }

    public List<PlaceDto> getMakersByJPQLFallback(String regionCode, String youtuberNm, Throwable throwable) {
        // JPQL 쿼리 작성

        String jpql = "SELECT new com.youtube_shorts_map.dto.PlaceDto(" +
                                                                        "p.id" +
                                                                        ", p.name" +
                                                                        ", p.roadAddress" +
                                                                        ", p.lotAddress" +
                                                                        ",p.latitude" +
                                                                        ", p.longitude" +
                                                                        ", y.name" +
                                                                        ", v.youtubeUrl" +
                                                                        ", v.description" +
                                                                        ", v.videoId" +
                                                                        ") " +
                "from VideoPlace vp " +
                "Join vp.video v " +
                "join v.youtuber y " +
                "join v.city c " +
                "join vp.place p " +
                "where y.name = :youtuberNm and c.regionCode = :regionCode";


        TypedQuery<PlaceDto> query = entityManager.createQuery(jpql, PlaceDto.class);
        query.setParameter("regionCode", regionCode);
        query.setParameter("youtuberNm", youtuberNm);

        // 결과 조회 및 반환
        return query.getResultList();
    }


}
