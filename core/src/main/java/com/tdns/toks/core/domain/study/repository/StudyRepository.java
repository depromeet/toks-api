package com.tdns.toks.core.domain.study.repository;

import com.tdns.toks.core.domain.study.model.entity.Study;
import com.tdns.toks.core.domain.study.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {
}
