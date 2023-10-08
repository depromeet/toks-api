package com.tdns.toks.core.domain.auth;

import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.auth.model.AuthUserImpl;
import com.tdns.toks.core.domain.user.type.UserRole;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class AuthUserValidator {
    @NonNull
    public static boolean isAuthenticated(@Nullable AuthUser authUser) {
        return authUser != null;
    }

    @NonNull
    public static Long getUidOrElseDefault(@Nullable AuthUser authUser) {
        if (isAuthenticated(authUser)) {
            return authUser.getId();
        }
        return -1L;
    }

    @NonNull
    public static AuthUser getOrElseDefault(@Nullable AuthUser authUser) {
        if (isAuthenticated(authUser)) {
            return authUser;
        }
        return new AuthUserImpl(-1L, UserRole.ANONYMOUS);
    }
}
