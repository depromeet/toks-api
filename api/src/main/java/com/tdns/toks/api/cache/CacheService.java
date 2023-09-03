package com.tdns.toks.api.cache;

import com.tdns.toks.core.common.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CacheService {
    private final StringRedisTemplate redisTemplate;

    public <T> T getOrNull(Cache<T> cache) {
        var data = redisTemplate.opsForValue().get(cache.getKey());
        return (data != null) ? MapperUtil.readValue(data, cache.getType()) : null;
    }

    @SneakyThrows
    public <T> T get(Cache<T> cache, Callable<T> callable) {
        var data = getOrNull(cache);

        if (data == null) {
            var calledData = callable.call();

            asyncSet(cache, calledData);

            return calledData;
        } else {
            return data;
        }
    }

    public <T> void set(Cache<T> cache, T data) {
        redisTemplate.opsForValue().set(
                cache.getKey(),
                MapperUtil.writeValueAsString(data),
                cache.getDuration()
        );
    }

    public <T> void asyncSet(Cache<T> cache, T data) {
        CompletableFuture.runAsync(() -> set(cache, data));
    }

    public <T> void delete(Cache<T> cache) {
        redisTemplate.delete(cache.getKey());
    }

    public <T> void asyncDelete(Cache<T> cache) {
        CompletableFuture.runAsync(() -> delete(cache));
    }

    public <T> Long increment(Cache<T> cache) {
        return redisTemplate.opsForValue().increment(cache.getKey());
    }

    public <T> Long decrement(Cache<T> cache) {
        return redisTemplate.opsForValue().decrement(cache.getKey());
    }

    public <T> Integer count(Cache<Integer> cache) {
        var count = getOrNull(cache);
        return count != null ? count : 0;
    }

    public <T> void asyncIncrement(Cache<T> cache) {
        CompletableFuture.runAsync(() -> increment(cache));
    }

    public <T> void asyncDecrement(Cache<T> cache) {
        CompletableFuture.runAsync(() -> decrement(cache));
    }
}
