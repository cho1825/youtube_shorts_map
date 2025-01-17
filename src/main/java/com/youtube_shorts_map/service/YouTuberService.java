package com.youtube_shorts_map.service;

import com.youtube_shorts_map.domain.entity.City;
import com.youtube_shorts_map.domain.entity.CityYoutuber;
import com.youtube_shorts_map.domain.entity.Youtuber;
import com.youtube_shorts_map.dto.YoutuberDto;
import com.youtube_shorts_map.repository.CityYoutuberRepository;
import com.youtube_shorts_map.repository.YouTuberRepository;
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
public class YouTuberService {

    @PersistenceContext
    private EntityManager entityManager;
    private final CityYoutuberRepository cityYoutuberRepository;



    @Cacheable(value = "youtuberCache", key = "'youtuber'")
    public List<YoutuberDto> getYoutubers(Long cityId) {

        City city = City.builder().id(cityId).build();

        List<CityYoutuber> cityYoutubers = cityYoutuberRepository.findByCity(city);

        List<YoutuberDto> youtuberDtoList = cityYoutubers.stream()
                .map(CityYoutuber::getYoutuber)
                .filter(youtuber -> "N".equals(youtuber.getDeleted()))
//                .map(youtuber -> convertToYoutuberDto(youtuber))
                .map(this::convertToYoutuberDto)
                .collect(Collectors.toList());

        return youtuberDtoList;
    }


    public List<YoutuberDto> getYoutubersWithPlaceCnt(Long cityId) {

        String jpql = "SELECT new com.youtube_shorts_map.dto.YoutuberDto(" +
                                                                            "y.id" +
                                                                            ", y.name" +
                                                                            ", y.channelId" +
                                                                            ", y.description" +
                                                                            ", COUNT(vp.id)" +
                                                                            ") " +
                        "from VideoPlace vp " +
                        "join vp.video v " +
                        "join v.youtuber y " +
                        "where v.city.id = :cityId " +
                        "and y.deleted = 'N'" +
                        "GROUP BY y.id, y.name " +
                        "ORDER BY COUNT(vp.id)";


        TypedQuery<YoutuberDto> query = entityManager.createQuery(jpql, YoutuberDto.class);
        query.setParameter("cityId", cityId);
        return query.getResultList();
    }

    private YoutuberDto convertToYoutuberDto(Youtuber youtuber) {
        return YoutuberDto.builder()
                .id(youtuber.getId())
                .name(youtuber.getName())
                .channelId(youtuber.getChannelId())
                .description(youtuber.getDescription())
                .build();
    }
}
