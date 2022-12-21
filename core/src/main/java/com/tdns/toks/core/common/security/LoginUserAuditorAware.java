package com.tdns.toks.core.common.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;

@Component
public class LoginUserAuditorAware implements AuditorAware<Long> {
	@Override
	public Optional<Long> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null == authentication || !authentication.isAuthenticated()) {
			return null;
		}
		return Optional.of(UserDetailDTO.get().getId());
	}
}
