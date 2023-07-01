package com.tdns.toks.api.domain.tag.event.subscribe;

import com.tdns.toks.api.domain.tag.event.model.TagEventModel;
import com.tdns.toks.api.domain.tag.event.model.TagsEventModel;
import com.tdns.toks.api.domain.tag.service.TagDictionaryEventService;
import com.tdns.toks.core.common.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TagDictionaryEventSubscribe {
    private final TagDictionaryEventService tagDictionaryEventService;

    @Async(value = "tagDictionaryExecutor")
    @EventListener(TagsEventModel.class)
    public void subscribe(TagsEventModel model) {
        var events = model.getTags()
                .stream()
                .map(tagName -> MapperUtil.writeValueAsString(new TagEventModel(tagName, model.getStudyId())))
                .collect(Collectors.toList());

        tagDictionaryEventService.push(events);

        log.info("tag dictionary to Tag Event Queue Complete");
    }
}
