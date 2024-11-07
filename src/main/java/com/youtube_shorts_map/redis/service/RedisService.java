package com.youtube_shorts_map.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final LettuceConnectionFactory redisConnectionFactory;

    @Autowired
    public RedisService(LettuceConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    public void connect() {
        RedisConnection connection = redisConnectionFactory.getConnection();
        if (!connection.isClosed()) {
            System.out.println("Redis에 이미 연결되어 있습니다잉!");
        } else {
            System.out.println("Redis에 새로 연결을 시도합니다잉!");
            redisConnectionFactory.afterPropertiesSet();  // 수동으로 연결을 재설정
        }
    }

    public void disconnect() {
        RedisConnection connection = redisConnectionFactory.getConnection();
        if (!connection.isClosed()) {
            System.out.println("Redis 연결을 해제합니다잉!");
            connection.close();  // 개별 연결 해제
        } else {
            System.out.println("이미 Redis 연결이 해제되어 있습니다잉!");
        }
    }

    public boolean isConnected() {
        return !redisConnectionFactory.getConnection().isClosed();
    }
}