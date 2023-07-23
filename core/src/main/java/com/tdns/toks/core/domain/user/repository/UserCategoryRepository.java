package com.tdns.toks.core.domain.user.repository;

import com.tdns.toks.core.domain.user.entity.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    @Transactional(readOnly = true)
    Optional<UserCategory> findByUserId(long userId);
}
