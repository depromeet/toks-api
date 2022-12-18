package com.tdns.toks.core.domain.study.repository;

import com.tdns.toks.core.domain.study.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByIdIn(List<Long> tagIdList);

    List<Tag> findByNameContaining(@Param("name") String name);

    Tag findFirstByName(String keyword);

    List<Tag> findByName(List<String> keywordList);
}
