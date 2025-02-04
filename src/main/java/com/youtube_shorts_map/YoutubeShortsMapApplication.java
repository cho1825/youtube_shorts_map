package com.youtube_shorts_map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class YoutubeShortsMapApplication {

    public static void main(String[] args) {
        SpringApplication.run(YoutubeShortsMapApplication.class, args);
    }

}
