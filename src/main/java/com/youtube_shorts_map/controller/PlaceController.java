package com.youtube_shorts_map.controller;

import com.youtube_shorts_map.dto.PlaceDto;
import com.youtube_shorts_map.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173,http://192.168.0.177:5173, http://192.168.219.146:5173") // 허용할 프론트엔드 도메인
public class PlaceController {

    private final PlaceService placeService;

//    @RequestMapping("/api/makers")
//    public ResponseEntity<List<PlaceDto>> getMakers(
//              @RequestParam String regionCode
//            , @RequestParam String youtuberNm
//            , @RequestParam(defaultValue = "all", required = false) String category) {
//
//        // 이게 성능이 더 빠름
//        List<PlaceDto> placeDtoList = placeService.getMakersByJPQL(regionCode, youtuberNm, category);
//
//        return ResponseEntity.ok(placeDtoList);
//    }
}
