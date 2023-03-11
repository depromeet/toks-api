package com.tdns.toks.core.domain.study.repository;

import com.tdns.toks.core.domain.study.model.entity.StudyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyUserRepository extends JpaRepository<StudyUser, Long>, StudyUserCustomRepository {
    boolean existsByUserIdAndStudyId(long userId, long studyId);

    List<StudyUser> findAllByUserId(long userId);

    List<StudyUser> findAllByStudyId(long studyId);

    @Query(value = "select su from StudyUser su where su.userId = :userId and su.status = 'ACTIVE'")
    List<StudyUser> findAllJoinedStudyByUserId (@Param("userId") long userId);

    Optional<StudyUser> findByStudyIdAndUserId(long studyId, long userId);
}
