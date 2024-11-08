package com.youtube_shorts_map.service;

import com.youtube_shorts_map.domain.entity.City;
import com.youtube_shorts_map.dto.CityDto;
import com.youtube_shorts_map.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    @Cacheable(value = "cityCache", key = "'city'")
    public List<CityDto> getCitiesWithYoutubers() {

        List<City> allCitiesWithYoutubers = cityRepository.findAllCitiesWithYoutubers();

        List<CityDto> cityDtos = allCitiesWithYoutubers.stream()
                .map(city -> CityDto.builder()
                        .id(city.getId())
                        .name(city.getName())
                        .regionCode(city.getRegionCode())
                        .build())
                .collect(Collectors.toList());

        return cityDtos;
    }
}
