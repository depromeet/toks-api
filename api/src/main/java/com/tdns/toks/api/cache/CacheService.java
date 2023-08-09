package com.tdns.toks.api.cache;

import com.tdns.toks.core.common.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CacheService {
    private final StringRedisTemplate redisTemplate;

    public <T> T getOrNull(Cache<T> cache) {
        var data = redisTemplate.opsForValue().get(cache.getKey());
        return (data != null) ? MapperUtil.readValue(data, cache.getType()) : null;
    }

    public <T> void set(Cache<T> cache, T data) {
        redisTemplate.opsForValue().set(cache.getKey(), MapperUtil.writeValueAsString(data), Duration.ofMinutes(3));
    }

    @Async
    public <T> void asyncSet(Cache<T> cache, T data) {
        CompletableFuture.runAsync(() -> set(cache, data));
    }

    public <T> void delete(Cache<T> cache) {
        redisTemplate.delete(cache.getKey());
    }

    public <T> void increment(Cache<T> cache) {
        redisTemplate.opsForValue().increment(cache.getKey());
    }
}
