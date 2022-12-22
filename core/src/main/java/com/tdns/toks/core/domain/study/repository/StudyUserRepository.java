package com.tdns.toks.core.domain.study.repository;

import com.tdns.toks.core.domain.study.model.entity.StudyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyUserRepository extends JpaRepository<StudyUser, Long> {
    boolean existsByUserIdAndStudyId(long userId, long studyId);

    List<StudyUser> findAllByUserId(long userId);

    List<StudyUser> findAllByStudyId(long studyId);
}
