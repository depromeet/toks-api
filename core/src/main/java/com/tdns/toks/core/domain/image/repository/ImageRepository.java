package com.tdns.toks.core.domain.image.repository;

import com.tdns.toks.core.domain.image.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
