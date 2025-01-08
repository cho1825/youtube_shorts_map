package com.youtube_shorts_map.collector.videoFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtube_shorts_map.domain.entity.Place;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class KakaoApi {


    public static Place getPlace(String placeNm, String kakaoApiKey) {

        String url = "https://dapi.kakao.com/v2/local/search/keyword.json";
        // 좌표와 반경 설정
        double latitude = 36.4491635278272; // 대전광역시 중심 위도
        double longitude = 127.426981067055; // 대전광역시 중심 경도

        int radius = 20000; // 20km 반경
        StringBuilder sb = new StringBuilder()
                .append(url)
                .append("?")
                .append("y=")
                .append(latitude)
                .append("&x=")
                .append(longitude)
                .append("&radius=")
                .append(radius)
                .append("&query=")
                .append(placeNm);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("y", latitude)
                .queryParam("x", longitude)
                .queryParam("radius", radius)
                .queryParam("query", placeNm);


        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        // API 호출 및 응답을 String으로 받기
        ResponseEntity<String> response = restTemplate.exchange(
//                builder.toUriString(),
                sb.toString(),
                HttpMethod.GET,
                entity,
                String.class
        );


        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.getBody());

            // documents 배열에서 첫 번째 장소 정보 추출
            JsonNode documents = root.path("documents");
            if (documents.isArray() && documents.size() > 0) {
                JsonNode firstPlace = documents.get(0);

                // JSON에서 필요한 필드 추출
                String placeName = firstPlace.path("place_name").asText();
                String addressName = firstPlace.path("address_name").asText();
                String roadAddressName = firstPlace.path("road_address_name").asText();
                String phone = firstPlace.path("phone").asText();
                String kakaoMapUrl = firstPlace.path("place_url").asText();
                Double x = firstPlace.path("x").asDouble();
                Double y = firstPlace.path("y").asDouble();

                Place place = Place.builder()
                        .name(placeName)
                        .lotAddress(addressName)
                        .roadAddress(roadAddressName)
                        .latitude(y)
                        .longitude(x)
                        .mapUrl(kakaoMapUrl)
                        .phoneNumber(phone)
                        .build();

                return place;
            }else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
