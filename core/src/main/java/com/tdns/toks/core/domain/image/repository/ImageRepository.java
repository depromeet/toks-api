package com.tdns.toks.core.domain.image.repository;

import com.tdns.toks.core.domain.image.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
