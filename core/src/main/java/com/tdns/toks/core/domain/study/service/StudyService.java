package com.tdns.toks.core.domain.study.service;

import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.study.model.entity.Study;
import com.tdns.toks.core.domain.study.model.entity.StudyTag;
import com.tdns.toks.core.domain.study.model.entity.StudyUser;
import com.tdns.toks.core.domain.study.model.entity.Tag;
import com.tdns.toks.core.domain.study.repository.StudyRepository;
import com.tdns.toks.core.domain.study.repository.StudyTagRepository;
import com.tdns.toks.core.domain.study.repository.StudyUserRepository;
import com.tdns.toks.core.domain.study.repository.TagRepository;
import com.tdns.toks.core.domain.study.type.StudyStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;

    private final StudyUserRepository studyUserRepository;

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
        List<Tag> tags = tagRepository.findByNameIn(keywordList);
        if (tags.size() != keywordList.size()) {
            Map<String, Long> tagNameIdMap = tags.stream().collect(Collectors.toMap(Tag::getName, Tag::getId));
            tags.addAll(keywordList.stream()
                    .filter(keyword -> !tagNameIdMap.containsKey(keyword))
                    .map(keyword -> createTag(convertToEntity(keyword)))
                    .collect(Collectors.toList()));
        }
        return tags;
    }

    private Tag convertToEntity(String keyword) {
        return Tag.builder()
                .name(keyword)
                .build();
    }

    public Study getStudy(long studyId) {
        return studyRepository.findById(studyId).orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.COULDNT_FIND_ANY_DATA));
    }

    public StudyUser saveStudyUser(StudyUser studyUser) {
        return studyUserRepository.save(studyUser);
    }

    @Transactional(readOnly = true)
    public boolean existStudyUser(long userId, long studyId) {
        return studyUserRepository.existsByUserIdAndStudyId(userId, studyId);
    }

    public boolean isFinishedStudy(Long studyId) {
        Study study = getStudy(studyId);
        return study.getStatus() == StudyStatus.FINISH;
    }
}
