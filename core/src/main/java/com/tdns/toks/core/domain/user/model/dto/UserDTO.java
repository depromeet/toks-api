package com.tdns.toks.core.domain.user.model.dto;

import com.tdns.toks.core.domain.user.model.entity.User;
import com.tdns.toks.core.domain.user.type.UserProvider;
import com.tdns.toks.core.domain.user.type.UserRole;
import com.tdns.toks.core.domain.user.type.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String nickname;
    private UserStatus status;
    private UserRole userRole;
    private UserProvider provider;
    private String providerId;
    private String refreshToken;

    public static UserDTO convertEntityToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.id = user.getId();
        userDTO.email = user.getEmail();
        userDTO.nickname = user.getNickname();
        userDTO.status = user.getStatus();
        userDTO.userRole = user.getUserRole();
        userDTO.provider = user.getProvider();
        userDTO.providerId = user.getProviderId();
        userDTO.refreshToken = user.getRefreshToken();
        return userDTO;
    }
}
