package com.tdns.toks.core.common.security.oauth;

import com.tdns.toks.core.common.security.JwtTokenProvider;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import com.tdns.toks.core.domain.user.model.entity.User;
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

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

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
        var jwtTokenPair = jwtTokenProvider.generateTokenPair(user.getId(), user.getEmail());
        user.setRefreshToken(jwtTokenPair.getRefreshToken());
        userRepository.save(user);
        return new UserDetailDTO(user, oAuth2Attribute.getAttributes(), jwtTokenPair);
    }

    private User createUser(OAuth2Attribute oAuth2Attribute) {
        return userRepository.save(convertToUserEntity(oAuth2Attribute));
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
