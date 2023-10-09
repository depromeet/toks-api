package com.tdns.toks.core.domain.auth.model;

import com.tdns.toks.core.domain.user.type.UserRole;
import lombok.Data;

@Data
public class AuthUserImpl implements AuthUser {
    private final Long id;
    private final UserRole userRole;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public UserRole getUserRole() {
        return this.userRole;
    }

    public static AuthUser unauthorizedUser() {
        return new AuthUserImpl(-1L, UserRole.ANONYMOUS);
    }
}
