package com.tdns.toks.api.domain.study.event.publish;

import com.tdns.toks.api.domain.study.event.model.TagsEventModel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TagDictionaryEventPublish {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(Long studyId, List<String> tagName) {
        var event = new TagsEventModel(studyId, tagName);
        applicationEventPublisher.publishEvent(event);
    }
}
