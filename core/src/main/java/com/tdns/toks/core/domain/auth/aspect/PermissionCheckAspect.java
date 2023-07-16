package com.tdns.toks.core.domain.auth.aspect;

import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PermissionCheckAspect {
    @Before("@annotation(com.tdns.toks.core.domain.auth.annotation.AdminPermission)")
    public void checkAdminPermission(JoinPoint joinPoint) {
        for (var args : joinPoint.getArgs()) {
            if (args instanceof AuthUser) {
                if (!((AuthUser) args).getUserRole().isAdmin()) {
                    throw new ApplicationErrorException(ApplicationErrorType.NOT_AUTHORIZED_ADMIN_USER);
                }
            }
        }
    }
}
