package com.tdns.toks.core.domain.study.service;

import com.tdns.toks.core.domain.study.model.entity.Study;
import com.tdns.toks.core.domain.study.model.entity.StudyTag;
import com.tdns.toks.core.domain.study.model.entity.Tag;
import com.tdns.toks.core.domain.study.repository.StudyRepository;
import com.tdns.toks.core.domain.study.repository.StudyTagRepository;
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

    public Study save(Study study) {
        return studyRepository.save(study);
    }

    public List<Tag> findByTagIdIn(List<Long> tagIdList) {
        return studyRepository.findByTagIdIn(tagIdList);
    }

    public List<StudyTag> saveAllStudyTag(List<StudyTag> studyTagList) {
        return studyTagRepository.saveAll(studyTagList);
    }
}
