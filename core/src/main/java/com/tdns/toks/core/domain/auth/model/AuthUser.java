package com.tdns.toks.core.domain.auth.model;

import com.tdns.toks.core.domain.user.type.UserRole;

/**
 * Toks Auth User
 */
public interface AuthUser {
    Long getId();

    UserRole getUserRole();
}
