package com.youtube_shorts_map.collector.videoFactory;

import com.youtube_shorts_map.domain.entity.Place;
import com.youtube_shorts_map.domain.entity.Video;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MakaVideoFactory extends VideoInfoFactory{

    @Override
    public List<Place> getPlaceInfo(Video video, String kakaoApiKey) {
        String text = video.getDescription();
        List<Place> places = new ArrayList<>();
        // 프랜차이즈 단어가 포함된 경우 null 반환
        if (isFranchise(text)) {
            return null;
        }

        if (text.contains("-")){
            String[] split = text.split("-");
            Place place = searchByExtractedText(split[0], kakaoApiKey);
            if (place != null) {
                places.add(place);
                return places;
            }
        }

        if (text.contains("1.")) {
            String[] entries = text.split("\\d+\\.\\s");
            for (String entry : entries) {
                if (!entry.trim().isEmpty()) { // 빈 항목 방지
                    // "-" 기호 앞의 부분을 추출
                    String storeName = entry.split("-")[0];
                    Place place = searchByExtractedText(storeName, kakaoApiKey);
                    if (place != null) {
                        places.add(place);
                    }
                }
            }
            return places;
        }

        return null;
    }

    private boolean isFranchise(String text) {
        // 프랜차이즈 관련 단어가 포함되어 있는지 검사
        String[] franchises = {"스타벅스", "롯데리아", "버거킹", "서브웨이", "맥도날드", "피자마루", "맘스터치", "본죽", "아임일리터", "지코바", "성심당", "프랭크버거", "베스킨라빈스", "페리카나"};
        for (String franchise : franchises) {
            if (text.contains(franchise)) {
                return true;
            }
        }
        return false;
    }


    private Place searchByExtractedText(String text, String kakaoApiKey) {
        // 카카오맵 api 연동 주소 검색
        return KakaoApi.getPlace(text,kakaoApiKey);
    }
}