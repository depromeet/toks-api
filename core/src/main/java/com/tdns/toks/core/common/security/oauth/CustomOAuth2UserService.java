package com.tdns.toks.core.common.security.oauth;

import com.tdns.toks.core.common.security.JwtTokenProvider;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import com.tdns.toks.core.domain.user.model.entity.User;
import com.tdns.toks.core.domain.user.repository.UserRepository;
import com.tdns.toks.core.domain.user.type.UserRole;
import com.tdns.toks.core.domain.user.type.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
        log.info("[CustomOAuth2UserService]");
        log.info(oAuth2User.toString());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(registrationId, oAuth2User.getAttributes());
        User user = userRepository.findByEmail(oAuth2Attribute.getEmail())
                                    .orElseGet(() -> createUser(oAuth2Attribute));


        return new UserDetailDTO(user, oAuth2Attribute.getAttributes(), jwtTokenProvider.generateToken(oAuth2Attribute.getEmail()));
    }

    private User createUser(OAuth2Attribute oAuth2Attribute) {
        return userRepository.save(convertToUserEntity(oAuth2Attribute));
    }

    private User convertToUserEntity(OAuth2Attribute oAuth2Attribute) {
        return User.builder()
                .email(oAuth2Attribute.getEmail())
                .name(oAuth2Attribute.getName())
                .status(UserStatus.ACTIVE)
                .userRole(UserRole.USER)
                .provider(oAuth2Attribute.getProvider())
                .providerId(oAuth2Attribute.getProviderId())
                .build();
    }
}