package com.tdns.toks.core.domain.study.service;

import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.study.model.entity.Study;
import com.tdns.toks.core.domain.study.model.entity.StudyUser;
import com.tdns.toks.core.domain.study.repository.StudyRepository;
import com.tdns.toks.core.domain.study.repository.StudyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;

    private final StudyUserRepository studyUserRepository;

    public Study save(Study study) {
        return studyRepository.save(study);
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
}
