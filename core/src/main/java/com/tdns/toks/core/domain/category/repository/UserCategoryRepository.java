package com.tdns.toks.core.domain.category.repository;

import com.tdns.toks.core.domain.category.entity.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    Optional<UserCategory> findByUserId(long userId);
}
