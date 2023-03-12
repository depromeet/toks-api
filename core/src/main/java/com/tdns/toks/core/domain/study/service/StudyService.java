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
import com.tdns.toks.core.domain.user.model.entity.User;
import com.tdns.toks.core.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyService {
    private final UserRepository userRepository;

    private final StudyRepository studyRepository;

    private final StudyUserRepository studyUserRepository;

    private final StudyTagRepository studyTagRepository;

    private final TagRepository tagRepository;

    public Study save(Study study) {
        if (!studyDatesValidate(study.getStartedAt(), study.getEndedAt())) {
            throw new SilentApplicationErrorException(ApplicationErrorType.INVALID_REQUEST, "스터디 시작일이 종료일 보다 앞섭니다.");
        }
        return studyRepository.save(study);
    }

    private boolean studyDatesValidate(LocalDateTime startDate, LocalDateTime endDate) {
        return startDate.isBefore(endDate);
    }

    public List<StudyTag> saveAllStudyTag(List<StudyTag> studyTags) {
        return studyTagRepository.saveAll(studyTags);
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
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_STUDY));
    }

    @Transactional(readOnly = true)
    public void isExists(final Long id) {
        var isExistsStudy = studyRepository.existsById(id);

        if (!isExistsStudy) {
            throw new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_STUDY);
        }
    }

    // 태그명이 중복인 경우 체크, 태그가 공백 데이터가 있는 경우, 태그가 공백인 경우 등을 제외시킴
    public List<Tag> getOrCreateTagListByKeywordList(List<String> keywordList) {
        var keywords = keywordList
                .stream()
                .map(tag -> tag.replaceAll(" ", ""))
                .filter(tag -> !tag.isBlank())
                .collect(Collectors.toSet());

        var tags = tagRepository.findByNameIn(keywords);

        if (tags.size() == keywords.size()) {
            return tags;
        }

        var tagNameIdMap = tags.stream()
                .collect(Collectors.toMap(Tag::getName, Tag::getId));

        var newTags = keywords.stream()
                .filter(keyword -> !tagNameIdMap.containsKey(keyword))
                .map(this::convertToEntity)
                .collect(Collectors.toList());

        var savedTags = tagRepository.saveAll(newTags);

        tags.addAll(savedTags);

        return tags;
    }

    private Tag convertToEntity(String keyword) {
        return Tag.builder()
                .name(keyword)
                .build();
    }

    public Study getStudy(long studyId) {
        return studyRepository.findById(studyId)
                .orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.NOT_FOUND_STUDY, "not found study " + studyId));
    }

    public List<Study> findAllStudiesByStatus(List<Long> studyIds, List<StudyStatus> studyStatuses) {
        return studyRepository.retrieveByIdsAndStatuses(studyIds, studyStatuses);
    }

    public StudyUser saveStudyUser(StudyUser studyUser) {
        return studyUserRepository.save(studyUser);
    }

    @Transactional(readOnly = true)
    public boolean existStudyUser(long userId, long studyId) {
        return studyUserRepository.existsByUserIdAndStudyId(userId, studyId);
    }

    public List<User> getUsersInStudy(Long studyId) {
        return studyUserRepository.findAllByStudyId(studyId)
                .stream()
                .map(studyUser -> userRepository.findById(studyUser.getUserId())
                        .orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.NOT_FOUND_USER)))
                .collect(Collectors.toList());
    }

    public Long deleteAllByLeaderId(Long leaderId) {
        return studyRepository.deleteAllByLeaderId(leaderId);
    }

    public Long countNewStudyCount() {
        var endAt = LocalDateTime.now();
        var startAt = endAt.minusDays(1);
        return studyRepository.countByCreatedAtBetween(startAt, endAt);
    }
}
