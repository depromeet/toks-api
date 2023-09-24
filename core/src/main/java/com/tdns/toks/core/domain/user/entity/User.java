package com.tdns.toks.core.domain.user.entity;

import com.tdns.toks.core.common.model.entity.BaseTimeEntity;
import com.tdns.toks.core.domain.user.type.UserProvider;
import com.tdns.toks.core.domain.user.type.UserRole;
import com.tdns.toks.core.domain.user.type.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, columnDefinition = "VARCHAR(255) COMMENT '회원 이메일'")
    private String email;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '회원 닉네임'")
    private String nickname;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '작은 프로필 이미지 URL'")
    private String thumbnailImageUrl;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '큰 프로필 이미지 URL'")
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10) COMMENT '회원 상태'")
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10) COMMENT '회원 권한'")
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10) COMMENT '인증 업체'")
    private UserProvider provider;

    @Column(columnDefinition = "VARCHAR(255) COMMENT 'user의 provider 고유 id'")
    private String providerId;

    @Column(columnDefinition = "VARCHAR(512) COMMENT '리프래시 토큰'")
    private String refreshToken;

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateUserRefreshTokenAndProfileUrl(String refreshToken, String profileImageUrl) {
        this.refreshToken = refreshToken;
        this.profileImageUrl = profileImageUrl;
    }
}
