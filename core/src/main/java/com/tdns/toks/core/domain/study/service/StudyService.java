package com.tdns.toks.core.domain.study.service;

import com.tdns.toks.core.domain.rank.repository.RankingRepository;
import com.tdns.toks.core.domain.study.model.entity.Study;
import com.tdns.toks.core.domain.study.model.entity.StudyTag;
import com.tdns.toks.core.domain.study.model.entity.Tag;
import com.tdns.toks.core.domain.study.repository.StudyRepository;
import com.tdns.toks.core.domain.study.repository.StudyTagRepository;
import com.tdns.toks.core.domain.study.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;

    private final StudyTagRepository studyTagRepository;

    private final TagRepository tagRepository;

    private final RankingRepository rankingRepository;

    public Study save(Study study) {
        return studyRepository.save(study);
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

    public List<Tag> getStudyTags(Long studyId) {
        return studyTagRepository.getStudyTagsByStudyId(studyId);
    }
}
