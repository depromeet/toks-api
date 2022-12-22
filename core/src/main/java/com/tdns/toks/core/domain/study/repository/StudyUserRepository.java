package com.tdns.toks.core.domain.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tdns.toks.core.domain.study.model.entity.StudyUser;

@Repository
public interface StudyUserRepository extends JpaRepository<StudyUser, Long>, StudyUserCustomRepository {
    boolean existsByUserIdAndStudyId(long userId, long studyId);
}
