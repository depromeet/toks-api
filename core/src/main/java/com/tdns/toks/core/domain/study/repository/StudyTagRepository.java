package com.tdns.toks.core.domain.study.repository;

import com.tdns.toks.core.domain.study.model.entity.StudyTag;
import com.tdns.toks.core.domain.study.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyTagRepository extends JpaRepository<StudyTag, Long> {

    @Query("select t from StudyTag st left join Tag t on t.id = st.tagId where st.studyId = :studyId")
    List<Tag> getStudyTagsByStudyId(@Param("studyId") Long studyId);
}
