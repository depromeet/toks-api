package com.tdns.toks.core.domain.type;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ANONYMOUS(ROLES.ANONYMOUS, "비로그인"),
    USER(ROLES.USER, "유저권한"),
    ADMIN(ROLES.ADMIN, "어드민권한");

    public static class ROLES {
        public static final String ANONYMOUS = "ANONYMOUS";
        public static final String USER = "USER";
        public static final String ADMIN = "ADMIN";
    }

    private String authority;
    private String description;

    private UserRole(String authority, String description) {
        this.authority = authority;
        this.description = description;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public String getDescription() {
        return description;
    }
}

