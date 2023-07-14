package com.tdns.toks.core.domain.user.model.dto;

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
}
