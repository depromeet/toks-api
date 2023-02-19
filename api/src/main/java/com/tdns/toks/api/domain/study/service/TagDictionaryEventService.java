package com.tdns.toks.api.domain.study.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagDictionaryEventService {
    private final RedisTemplate<String, String> redisTemplate;

    private final static String TAG_DICTIONARY_EVENT_QUEUE = "tag:dictionary:event-queue";
    private static final int EVENT_QUEUE_MESSAGE_SIZE = 5;

    public List<String> pop() {
        var events = redisTemplate.opsForList()
                .rightPop(TAG_DICTIONARY_EVENT_QUEUE, EVENT_QUEUE_MESSAGE_SIZE);

        log.info("tag dictionary event queue pop, size is {}", EVENT_QUEUE_MESSAGE_SIZE);

        return events;
    }

    /**
     * @param events String으로 말아진 객체 데이터
     * @see com.tdns.toks.api.domain.study.event.model.TagEventModel
     */
    public void push(List<String> events) {
        redisTemplate.opsForList()
                .leftPushAll(TAG_DICTIONARY_EVENT_QUEUE, events);

        log.info("tag dictionary event queue push, size is {}", events.size());
    }
}
