package com.youtube_shorts_map.collector.enums;

public enum ApiFetchLimit {

    ALL,    // 전체 조회
    SIZE_50,  // 50개 조회
    SIZE_10,  // 10개 조회
    SIZE_5,  // 5개 조회
    SIZE_1;  // 1개 조회

    public int toInt() {
        switch (this) {
            case ALL:
                return Integer.MAX_VALUE; // 전체 조회를 의미하는 큰 값
            case SIZE_1:
                return 1;
            case SIZE_5:
                return 5;
            case SIZE_10:
                return 10;
            case SIZE_50:
                return 50;
            default:
                throw new IllegalArgumentException("Unknown FetchSize");
        }
    }
}
