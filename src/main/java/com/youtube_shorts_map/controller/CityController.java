package com.youtube_shorts_map.controller;

import com.youtube_shorts_map.dto.CityDto;
import com.youtube_shorts_map.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CityController {

    private final CityService cityService;

    @GetMapping("/city")
    public ResponseEntity<List<CityDto>> getCities(){
        List<CityDto> cityDtos = cityService.getCitiesWithYoutubers();

        return ResponseEntity.ok(cityDtos);
    }

}
