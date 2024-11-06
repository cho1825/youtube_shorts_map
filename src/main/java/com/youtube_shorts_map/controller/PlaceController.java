package com.youtube_shorts_map.controller;

import com.youtube_shorts_map.dto.PlaceDto;
import com.youtube_shorts_map.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @RequestMapping("/api/makers")
    public ResponseEntity<List<PlaceDto>> getMakers(@RequestParam String regionCode, @RequestParam String youtuberNm) {

        // 이게 성능이 더 빠름
        List<PlaceDto> placeDtoList = placeService.getMakersByJPQL(regionCode, youtuberNm);

        return ResponseEntity.ok(placeDtoList);
    }
}
