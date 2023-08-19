package com.tdns.toks.core.domain.tag.repository;

import com.tdns.toks.core.domain.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Transactional(readOnly = true)
    Optional<Tag> findByName(String name);
}
