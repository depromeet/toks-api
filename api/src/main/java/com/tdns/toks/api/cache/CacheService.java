package com.tdns.toks.api.cache;

import com.tdns.toks.core.common.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

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

    public <T> void delete(Cache<T> cache) {
        redisTemplate.delete(cache.getKey());
    }
}
