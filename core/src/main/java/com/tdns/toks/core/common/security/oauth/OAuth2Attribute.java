package com.tdns.toks.core.common.security.oauth;

import com.tdns.toks.core.domain.user.type.UserProvider;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class OAuth2Attribute {
    private Map<String, Object> attributes;
    private String attributeKey;
    private String email;
    private String nickname;
    private String thumbnailImageUrl;
    private String profileImageUrl;
    private UserProvider provider;
    private String providerId;

    static OAuth2Attribute of(String provider, Map<String, Object> attributes) {
        switch (provider) {
            case "kakao":
                return ofKakao("email", attributes);
            default:
                throw new RuntimeException("등록되지 않은 Provider : " + provider);
        }
    }

    private static OAuth2Attribute ofKakao(String attributeKey,
                                           Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        return OAuth2Attribute.builder()
                .provider(UserProvider.KAKAO)
                .providerId(String.valueOf(attributes.get("id")))
                .email((String) kakaoAccount.get("email"))
                .nickname((String) properties.get("nickname"))
                .thumbnailImageUrl((String) profile.get("thumbnail_image_url"))
                .profileImageUrl((String) profile.get("profile_image_url"))
                .attributes(kakaoAccount)
                .attributeKey(attributeKey)
                .build();
    }
}
