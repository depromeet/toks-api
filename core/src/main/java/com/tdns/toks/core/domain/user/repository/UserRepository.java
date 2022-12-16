package com.tdns.toks.core.domain.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tdns.toks.core.domain.user.model.entity.User;
import com.tdns.toks.core.domain.user.type.UserProvider;
import com.tdns.toks.core.domain.user.type.UserStatus;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {
    Optional<User> findByEmail(String email);

    boolean existsByNickname(String Nickname);

    Optional<User> findByEmailAndStatus(String email, UserStatus status);

    Optional<User> findByProviderAndProviderId(UserProvider provider, String providerId);

    @Query("select u from User u join QuizRank r on r.userId = u.id where r.studyId = :studyId")
    List<User> getUsersInStudy(@Param("studyId") Long studyId);
}
