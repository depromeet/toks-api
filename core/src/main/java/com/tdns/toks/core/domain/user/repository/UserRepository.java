package com.tdns.toks.core.domain.user.repository;

import com.tdns.toks.core.domain.user.model.entity.User;
import com.tdns.toks.core.domain.user.type.UserProvider;
import com.tdns.toks.core.domain.user.type.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String Nickname);

    Optional<User> findByEmailAndStatus(String email, UserStatus status);

    Optional<User> findByProviderAndProviderId(UserProvider provider, String providerId);
}
