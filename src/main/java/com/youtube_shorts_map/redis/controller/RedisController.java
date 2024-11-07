package com.youtube_shorts_map.redis.controller;

import com.youtube_shorts_map.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisService redisService;

    @Autowired
    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping("/connect")
    public String connectRedis() {
        if (redisService.isConnected()) {
            return "이미 Redis에 연결되어 있습니다잉!";
        } else {
            redisService.connect();
            return "Redis에 연결되었습니다잉!";
        }
    }

    @GetMapping("/disconnect")
    public String disconnectRedis() {
        if (!redisService.isConnected()) {
            return "이미 Redis 연결이 해제되어 있습니다잉!";
        } else {
            redisService.disconnect();
            return "Redis 연결을 해제했습니다잉!";
        }
    }

    @GetMapping("/status")
    public String checkConnectionStatus() {
        return redisService.isConnected() ? "Redis에 연결되어 있습니다잉!" : "Redis 연결이 해제되어 있습니다잉!";
    }
}