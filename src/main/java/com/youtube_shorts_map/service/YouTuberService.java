package com.youtube_shorts_map.service;

import com.youtube_shorts_map.domain.entity.City;
import com.youtube_shorts_map.domain.entity.CityYoutuber;
import com.youtube_shorts_map.domain.entity.Youtuber;
import com.youtube_shorts_map.dto.YoutuberDto;
import com.youtube_shorts_map.repository.CityYoutuberRepository;
import com.youtube_shorts_map.repository.YouTuberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class YouTuberService {

    private final YouTuberRepository youTuberRepository;
    private final CityYoutuberRepository cityYoutuberRepository;

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

    private YoutuberDto convertToYoutuberDto(Youtuber youtuber) {
        return YoutuberDto.builder()
                .id(youtuber.getId())
                .name(youtuber.getName())
                .channelId(youtuber.getChannelId())
                .description(youtuber.getDescription())
                .build();
    }
}
