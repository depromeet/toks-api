package com.tdns.toks.api.batch.job;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tdns.toks.api.domain.study.event.model.TagEventModel;
import com.tdns.toks.api.domain.study.service.TagDictionaryEventService;
import com.tdns.toks.core.common.utils.MapperUtil;
import com.tdns.toks.core.domain.study.model.entity.StudyTag;
import com.tdns.toks.core.domain.study.model.entity.Tag;
import com.tdns.toks.core.domain.study.repository.StudyTagRepository;
import com.tdns.toks.core.domain.study.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TagDictionaryJob {
    private final TagRepository tagRepository;
    private final StudyTagRepository studyTagRepository;
    private final TagDictionaryEventService tagDictionaryEventService;

    public void run() {
        var messages = tagDictionaryEventService.pop();

        if (Objects.requireNonNull(messages).isEmpty()) {
            return;
        }

        var events = Objects.requireNonNull(messages)
                .stream()
                .map(message -> MapperUtil.readValue(message, new TypeReference<TagEventModel>() {}))
                .collect(Collectors.toSet());

        savedTagJob(events);
        savedStudyTagJob(events);

        log.info("run tag dictionary complete");
    }

    /**
     * Tag 기록 저장
     *
     * @param events
     */
    private void savedTagJob(Set<TagEventModel> events) {
        var tags = tagRepository.findAll()
                .stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());

        var savedTags = events.stream()
                .filter(tag -> !tags.contains(tag.getTagName()))
                .map(tag -> new Tag(tag.getStudyId(), tag.getTagName()))
                .collect(Collectors.toList());

        tagRepository.saveAll(savedTags);

        log.info("saved New Tag Job Complete size : {}", savedTags.size());
    }

    /**
     * Study Tag Mapping Table 생성
     *
     * @param events
     */
    private void savedStudyTagJob(Set<TagEventModel> events) {
        var studyTags = events
                .stream()
                .map(tag -> new StudyTag(tag.getStudyId(), tagRepository.getByName(tag.getTagName()).getId()))
                .collect(Collectors.toList());

        studyTagRepository.saveAll(studyTags);

        log.info("saved study tag mapping data complete : {}", studyTags.size());
    }
}