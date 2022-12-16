package com.tdns.toks.core.domain.study.service;

import java.util.List;

import com.tdns.toks.core.domain.user.model.entity.User;
import com.tdns.toks.core.domain.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    private final StudyRepository studyRepository;

    private final StudyTagRepository studyTagRepository;

    private final TagRepository tagRepository;

    public Study save(Study study) {
        return studyRepository.save(study);
    }

    public Study getStudy(Long studyId) {
        return studyRepository.getById(studyId);
    }

    @Transactional(readOnly = true)
    public List<Tag> getTagListByIdList(List<Long> tagIdList) {
        return tagRepository.findByIdIn(tagIdList);
    }

    public List<StudyTag> saveAllStudyTag(List<StudyTag> studyTagList) {
        return studyTagRepository.saveAll(studyTagList);
    }

    @Transactional(readOnly = true)
    public List<Tag> getTagListByKeyword(String keyword) {
        return tagRepository.findByNameContaining(keyword);
    }

    @Transactional(readOnly = true)
    public Tag getTagByKeyword(String keyword) {
        return tagRepository.findFirstByName(keyword);
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional(readOnly = true)
    public Study getOrThrow(final Long id) {
        return studyRepository.findById(id)
            .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.INVALID_REQUEST));
    }

    public List<Tag> getStudyTags(Long studyId) {
        return studyTagRepository.getStudyTagsByStudyId(studyId);
    }

    public List<User> getUsersInStudy(Long studyId) {
        return userRepository.getUsersInStudy(studyId);
    }
}
