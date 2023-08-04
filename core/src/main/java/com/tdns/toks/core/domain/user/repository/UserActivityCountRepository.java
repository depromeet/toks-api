package com.tdns.toks.core.domain.user.repository;

import com.tdns.toks.core.domain.user.entity.UserActivityCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserActivityCountRepository extends JpaRepository<UserActivityCount, Long> {
    Optional<UserActivityCount> findByUserId(long userId);
}
