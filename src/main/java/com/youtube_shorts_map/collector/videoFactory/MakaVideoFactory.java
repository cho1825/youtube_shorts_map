package com.youtube_shorts_map.collector.videoFactory;

import com.youtube_shorts_map.domain.entity.Place;
import com.youtube_shorts_map.domain.entity.Video;

public class MakaVideoFactory extends VideoInfoFactory{

    @Override
    public Place getPlaceInfo(Video video) {
        String text = video.getDescription();

        // 프랜차이즈 단어가 포함된 경우 null 반환
        if (isFranchise(text)) {
            return null;
        }

        Place place;

        // 케이스 1: "가게이름 - 메뉴이름 (가격)"
        place = trySearchByPattern(text, "^[^\\d]+ - [^\\d]+ \\(\\d+원\\)$", text.split(" - ")[0]);
        if (place != null) {
            return place;
        }

        // 케이스 2: "가게이름 (가격)"
        place = trySearchByPattern(text, "^[^\\d]+ \\(\\d+원\\)$", text.substring(0, text.indexOf(" (")));
        if (place != null) {
            return place;
        }

        // 케이스 3: "가게이름 - 메뉴이름1 (가격) - 메뉴이름2 (가격)"
        place = trySearchByPattern(text, "^[^\\d]+ - [^\\d]+ \\(\\d+원\\)( - [^\\d]+ \\(\\d+원\\))+$", text.split(" - ")[0]);
        if (place != null) {
            return place;
        }

        // 케이스 4: 복합형 "지역명 가게이름 - 메뉴이름 (가격)"
        String extractedName = null;
        if (text.matches("^[^\\d]+ [^\\d]+ - [^\\d]+ \\(\\d+원\\)$")) {
            String[] parts = text.split(" - ");
            extractedName = parts[0].substring(parts[0].indexOf(" ") + 1);
        }
        if (extractedName != null) {
            place = searchByExtractedText(extractedName);
        }

        return place;
    }

    private boolean isFranchise(String text) {
        // 프랜차이즈 관련 단어가 포함되어 있는지 검사
        String[] franchises = {"스타벅스", "롯데리아", "버거킹", "서브웨이", "맥도날드", "피자마루", "맘스터치", "본죽", "아임일리터", "지코바"};
        for (String franchise : franchises) {
            if (text.contains(franchise)) {
                return true;
            }
        }
        return false;
    }

    private Place trySearchByPattern(String text, String pattern, String extractedName) {
        if (text.matches(pattern)) {
            return searchByExtractedText(extractedName);
        }
        return null;
    }

    private Place searchByExtractedText(String text) {
        // 카카오맵 api 연동 주소 검색
        return null;
    }
}