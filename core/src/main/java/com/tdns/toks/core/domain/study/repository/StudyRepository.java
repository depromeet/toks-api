package com.tdns.toks.core.domain.study.repository;

import com.tdns.toks.core.domain.study.model.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {

    @Query("select s from Study s join QuizRank r on r.studyId = s.id where r.userId =:userId and s.status = 'IN_PROGRESS'")
    List<Study> getAllInProgressStudyByUserId(@Param("userId") Long userId);
}
