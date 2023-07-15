package com.tdns.toks.core.domain.user.repository;

import com.tdns.toks.core.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional(readOnly = true)
    Optional<User> findByEmail(String email);

    @Transactional(readOnly = true)
    boolean existsByNickname(String Nickname);

    @Transactional(readOnly = true)
    Long countByCreatedAtBetween(LocalDateTime startAt, LocalDateTime endAt);
}
