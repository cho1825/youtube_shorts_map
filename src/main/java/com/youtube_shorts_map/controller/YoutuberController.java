package com.youtube_shorts_map.controller;

import com.youtube_shorts_map.dto.YoutuberDto;
import com.youtube_shorts_map.service.YouTuberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class YoutuberController {

    private final YouTuberService youTuberService;

    @RequestMapping("/api/city/youtubers")
    public ResponseEntity<List<YoutuberDto>> getYoutubers(@RequestParam Long cityId){
        return ResponseEntity.ok(youTuberService.getYoutubers(cityId));
    }
}
