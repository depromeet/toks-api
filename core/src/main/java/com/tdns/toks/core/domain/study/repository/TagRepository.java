package com.tdns.toks.core.domain.study.repository;

import com.tdns.toks.core.domain.study.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Transactional(readOnly = true)
    List<Tag> findByIdIn(List<Long> tagIdList);

    @Transactional(readOnly = true)
    List<Tag> findByNameContaining(@Param("name") String name);

    @Transactional(readOnly = true)
    Tag findFirstByName(String keyword);

    @Transactional(readOnly = true)
    List<Tag> findByNameIn(List<String> keywordList);

    @Transactional(readOnly = true)
    List<Tag> findByNameIn(Set<String> keywords);

    @Transactional(readOnly = true)
    Boolean existsByName(String name);

    @Transactional(readOnly = true)
    Tag getByName(String name);

    @Transactional(readOnly = true)
    Long countByCreatedAtBetween(LocalDateTime startAt, LocalDateTime endAt);
}
