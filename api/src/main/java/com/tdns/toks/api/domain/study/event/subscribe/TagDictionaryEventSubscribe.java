package com.tdns.toks.api.domain.study.event.subscribe;

import com.tdns.toks.api.domain.study.event.model.TagEventModel;
import com.tdns.toks.api.domain.study.event.model.TagsEventModel;
import com.tdns.toks.core.common.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TagDictionaryEventSubscribe {
    private final RedisTemplate<String, String> redisTemplate;
    public final static String TAG_DICTIONARY_EVENT_QUEUE = "tag:dictionary:event-queue";

    @Async(value = "tagDictionaryExecutor")
    @EventListener(TagsEventModel.class)
    public void subscribe(TagsEventModel model) {
        var events = model.getTags()
                .stream()
                .map(tagName -> MapperUtil.writeValueAsString(new TagEventModel(tagName, model.getStudyId())))
                .collect(Collectors.toList());

        redisTemplate.opsForList()
                .leftPushAll(TAG_DICTIONARY_EVENT_QUEUE, events);

        log.info("tag dictionary to Tag Event Queue Complete");
    }
}
