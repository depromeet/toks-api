package com.tdns.toks.core.domain.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Admin 서비스 체크용 annotation
 * - 해당 어노테이션이 있는 경우 사용자의 권한이 Admin이어야 호출이 가능하다
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface AdminPermission {

}
