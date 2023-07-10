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

        if (data == null) {
            return null;
        } else {
            return MapperUtil.readValue(data, cache.getType());
        }
    }

    public <T> void set(Cache<T> cache, T data) {
        redisTemplate.opsForValue().set(cache.getKey(), MapperUtil.writeValueAsString(data), Duration.ofMinutes(3));
    }
}
