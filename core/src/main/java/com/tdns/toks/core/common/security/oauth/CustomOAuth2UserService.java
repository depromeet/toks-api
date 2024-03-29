package com.tdns.toks.core.common.security.oauth;

import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.security.JwtTokenProvider;
import com.tdns.toks.core.domain.quiz.repository.QuizReplyHistoryRepository;
import com.tdns.toks.core.domain.user.entity.User;
import com.tdns.toks.core.domain.user.entity.UserActivityCount;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import com.tdns.toks.core.domain.user.repository.UserActivityCountRepository;
import com.tdns.toks.core.domain.user.repository.UserRepository;
import com.tdns.toks.core.domain.user.type.UserRole;
import com.tdns.toks.core.domain.user.type.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserActivityCountRepository userActivityCountRepository;
    private final QuizReplyHistoryRepository quizReplyHistoryRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        var oAuth2UserService = new DefaultOAuth2UserService();
        var oAuth2User = oAuth2UserService.loadUser(userRequest);
        var oAuth2Attribute = OAuth2Attribute.of(
                userRequest.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes());
        var user = userRepository.findByEmail(oAuth2Attribute.getEmail())
                .orElseGet(() -> createUser(oAuth2Attribute));

        userActivityCountRepository.findByUserId(user.getId()).orElseGet(() -> createActivityCount(user.getId()));

        var jwtTokenPair = jwtTokenProvider.generateTokenPair(user.getId(), user.getEmail());
        if (user.getUpdatedAt().toLocalDate().isBefore(LocalDateTime.now().toLocalDate())) {
            userActivityCountRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_USER_ACTIVITY))
                    .updateTotalVisitCount();
        }

//        user.setRefreshToken(jwtTokenPair.getRefreshToken());
        user.updateUserRefreshTokenAndProfileUrl(jwtTokenPair.getRefreshToken(),
                oAuth2Attribute.getProfileImageUrl());

        userRepository.save(user);
        return new UserDetailDTO(user, oAuth2Attribute.getAttributes(), jwtTokenPair);
    }

    private User createUser(OAuth2Attribute oAuth2Attribute) {
        return userRepository.save(convertToUserEntity(oAuth2Attribute));
    }

    private UserActivityCount createActivityCount(long userId) {
        int quizCount = Math.toIntExact(quizReplyHistoryRepository.countByCreatedBy(userId));
        UserActivityCount u = new UserActivityCount(userId, userId,0, quizCount);
        return userActivityCountRepository.save(u);
    }

    private User convertToUserEntity(OAuth2Attribute oAuth2Attribute) {
        return User.builder()
                .email(oAuth2Attribute.getEmail())
                .nickname(oAuth2Attribute.getNickname())
                .status(UserStatus.ACTIVE)
                .userRole(UserRole.USER)
                .thumbnailImageUrl(oAuth2Attribute.getThumbnailImageUrl())
                .profileImageUrl(oAuth2Attribute.getProfileImageUrl())
                .provider(oAuth2Attribute.getProvider())
                .providerId(oAuth2Attribute.getProviderId())
                .build();
    }
}
