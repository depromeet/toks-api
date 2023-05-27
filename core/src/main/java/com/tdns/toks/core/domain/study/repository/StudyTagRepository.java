package com.tdns.toks.core.domain.study.repository;

import com.tdns.toks.core.domain.study.model.entity.StudyTag;
import com.tdns.toks.core.domain.study.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StudyTagRepository extends JpaRepository<StudyTag, Long> {
    @Transactional(readOnly = true)
    @Query("SELECT t FROM StudyTag st LEFT JOIN Tag t ON t.id = st.tagId WHERE st.studyId =:studyId")
    List<Tag> getStudyTagsByStudyId(@Param("studyId") Long studyId);
}
