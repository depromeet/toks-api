package com.tdns.toks.core.domain.auth;

import com.tdns.toks.core.domain.auth.model.AuthUser;

public class AuthUserValidator {
    public static boolean isAuthenticated(AuthUser authUser) {
        return authUser != null;
    }
}
