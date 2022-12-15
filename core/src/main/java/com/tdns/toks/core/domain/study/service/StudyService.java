package com.tdns.toks.core.domain.study.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.study.model.entity.Study;
import com.tdns.toks.core.domain.study.model.entity.StudyTag;
import com.tdns.toks.core.domain.study.model.entity.Tag;
import com.tdns.toks.core.domain.study.repository.StudyRepository;
import com.tdns.toks.core.domain.study.repository.StudyTagRepository;
import com.tdns.toks.core.domain.study.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;

    private final StudyTagRepository studyTagRepository;

    private final TagRepository tagRepository;

    public Study save(Study study) {
        return studyRepository.save(study);
    }

    public List<StudyTag> saveAllStudyTag(List<StudyTag> studyTagList) {
        return studyTagRepository.saveAll(studyTagList);
    }

    @Transactional(readOnly = true)
    public List<Tag> getTagListByKeyword(String keyword) {
        return tagRepository.findByNameContaining(keyword);
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional(readOnly = true)
    public Study getOrThrow(final Long id) {
        return studyRepository.findById(id)
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.INVALID_REQUEST));
    }

    public List<Tag> getOrCreateTagListByKeywordList(List<String> keywordList) {
        List<Tag> tagList = tagRepository.findByName(keywordList);
        if (tagList.size() != keywordList.size()) {
            List<String> tagNameList = tagList.stream().map(Tag::getName).collect(Collectors.toList());
            tagList.addAll(keywordList.stream()
                    .filter(keyword -> !tagNameList.contains(keyword))
                    .map(keyword -> createTag(convertToEntity(keyword)))
                    .collect(Collectors.toList()));
        }
        return tagList;
    }

    private Tag convertToEntity(String keyword) {
        return Tag.builder()
                .name(keyword)
                .build();
    }
}
