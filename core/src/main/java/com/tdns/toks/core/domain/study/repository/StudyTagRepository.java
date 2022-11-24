package com.tdns.toks.core.domain.study.repository;

import com.tdns.toks.core.domain.study.model.entity.StudyTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyTagRepository extends JpaRepository<StudyTag, Long> {
}
