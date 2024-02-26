package com.tdns.toks.core.domain.banner.repository;

import com.tdns.toks.core.domain.banner.entity.BottomBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BottomBannerRepository extends JpaRepository<BottomBanner, Long> {
    @Transactional(readOnly = true)
    List<BottomBanner> findAllByIsActive(Boolean isActive);
}
