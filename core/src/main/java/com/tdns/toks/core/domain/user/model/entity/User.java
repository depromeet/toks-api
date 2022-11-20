package com.tdns.toks.core.domain.user.model.entity;

import com.tdns.toks.core.common.model.entity.BaseTimeEntity;
import com.tdns.toks.core.domain.user.model.dto.UserDTO;
import com.tdns.toks.core.domain.user.type.UserProvider;
import com.tdns.toks.core.domain.user.type.UserRole;
import com.tdns.toks.core.domain.user.type.UserStatus;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "회원 번호")
    private Long id;

    @Column(name = "email", unique = true, columnDefinition = "회원 이메일")
    private String email;

    @Column(name = "nickname", columnDefinition = "회원 닉네임")
    private String nickname;

    @Column(name = "thumbnail_image_url", columnDefinition = "작은 프로필 이미지 URL")
    private String thumbnailImageUrl;

    @Column(name = "profile_image_url", columnDefinition = "큰 프로필 이미지 URL")
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "회원 상태")
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false, columnDefinition = "회원 권한")
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, columnDefinition = "provider")
    private UserProvider provider;

    @Column(name = "provider_id", columnDefinition = "user의 provider 고유 id")
    private String providerId;

    @Column(name = "refresh_token", columnDefinition = "리프레시 토큰")
    private String refreshToken;

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
