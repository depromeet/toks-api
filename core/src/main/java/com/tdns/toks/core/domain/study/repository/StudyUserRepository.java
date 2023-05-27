package com.tdns.toks.core.domain.study.repository;

import com.tdns.toks.core.domain.study.model.entity.StudyUser;
import com.tdns.toks.core.domain.study.type.StudyUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyUserRepository extends JpaRepository<StudyUser, Long>, StudyUserCustomRepository {
    @Transactional(readOnly = true)
    boolean existsByUserIdAndStudyId(long userId, long studyId);

    @Transactional(readOnly = true)
    List<StudyUser> findAllByUserId(long userId);

    @Transactional(readOnly = true)
    List<StudyUser> findAllByStudyId(long studyId);

    @Transactional(readOnly = true)
    @Query(value = "SELECT su FROM StudyUser su WHERE su.userId = :userId AND su.status = :joinedStatus")
    List<StudyUser> findAllJoinedStudyByUserId(@Param("userId") long userId, @Param("joinedStatus") StudyUserStatus joinedStatus);

    @Transactional(readOnly = true)
    Optional<StudyUser> findByStudyIdAndUserId(long studyId, long userId);
}
