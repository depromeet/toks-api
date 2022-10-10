package com.tdns.toks.core.domain.user.repository;

import com.tdns.toks.core.domain.user.model.entity.UserEntity;
import com.tdns.toks.core.domain.user.type.UserProvider;
import com.tdns.toks.core.domain.user.type.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    UserEntity findByName(String name);

    Optional<UserEntity> findByEmailAndStatus(String email, UserStatus status);

    Optional<UserEntity> findByProviderAndProviderId(UserProvider provider, String providerId);
}
