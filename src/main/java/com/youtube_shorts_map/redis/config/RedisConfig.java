package com.youtube_shorts_map.redis.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.concurrent.Callable;

@Configuration
@EnableCaching
@Slf4j
public class RedisConfig extends CachingConfigurerSupport {

    private static final int MAX_RECONNECT_ATTEMPTS = 5;

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10)) // 캐시 유효 기간 설정
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory), config) {
            @Override
            public Cache getCache(String name) {
                Cache cache = super.getCache(name);
                return new Cache() {
                    @Override
                    public String getName() {
                        return cache.getName();
                    }

                    @Override
                    public Object getNativeCache() {
                        return cache.getNativeCache();
                    }

                    @Override
                    public ValueWrapper get(Object key) {
                        ValueWrapper value = cache.get(key);
                        if (value != null) {
                            log.info("Redis에서 데이터를 가져왔습니다 - 캐시 이름: {}, 키: {}", name, key);
                        }
                        return value;
                    }

                    @Override
                    public <T> T get(Object key, Class<T> type) {
                        T value = cache.get(key, type);
                        if (value != null) {
                            log.info("Redis에서 데이터를 가져왔습니다 - 캐시 이름: {}, 키: {}", name, key);
                        }
                        return value;
                    }

                    @Override
                    public <T> T get(Object key, Callable<T> valueLoader) {
                        T value = cache.get(key, valueLoader);
                        if (value != null) {
                            log.info("Redis에서 데이터를 가져왔습니다 - 캐시 이름: {}, 키: {}", name, key);
                        }
                        return value;
                    }


                    @Override
                    public void put(Object key, Object value) {
                        cache.put(key, value);
                    }

                    @Override
                    public void evict(Object key) {
                        cache.evict(key);
                    }

                    @Override
                    public void clear() {
                        cache.clear();
                    }
                };
            }
        };
    }


    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                System.err.println("Redis GET error: " + e.getMessage());
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                System.err.println("Redis PUT error: " + e.getMessage());
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
                System.err.println("Redis EVICT error: " + e.getMessage());
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                System.err.println("Redis CLEAR error: " + e.getMessage());
            }
        };
    }

    @Bean(destroyMethod = "shutdown")
    public ClientResources clientResources() {
        return DefaultClientResources.create();
    }


    @Bean
    public LettuceConnectionFactory redisConnectionFactory(ClientResources clientResources) {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration("127.0.0.1", 6379);

        // 자동 재연결 비활성화 설정
        ClientOptions clientOptions = ClientOptions.builder()
                .autoReconnect(false)  // 자동 재연결 비활성화
                .build();
//                .autoReconnect(true)              // 자동 재연결 활성화
//                .pingBeforeActivateConnection(true) // 연결 활성화 전 ping 수행
//                .build();



        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .clientOptions(clientOptions)
                .clientResources(clientResources)
                .build();

        return new LettuceConnectionFactory(redisConfig, clientConfig);
    }



}
