package com.tdns.toks.api.domain.user.model.dto;

import com.tdns.toks.core.domain.user.entity.User;
import com.tdns.toks.core.domain.user.type.UserRole;
import com.tdns.toks.core.domain.user.type.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Long id;
    private String email;
    private String nickname;
    private String thumbnailImageUrl;
    private String profileImageUrl;
    private UserStatus status;
    private UserRole userRole;

    public static UserModel from(User user) {
        return new UserModel(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getThumbnailImageUrl(),
                user.getProfileImageUrl(),
                user.getStatus(),
                user.getUserRole()
        );
    }
}
