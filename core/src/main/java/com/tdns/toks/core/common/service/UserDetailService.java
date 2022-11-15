package com.tdns.toks.core.common.service;

import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.model.dto.UserDetailDTO;
import com.tdns.toks.core.domain.model.entity.User;
import com.tdns.toks.core.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userEntity = userRepository.findByEmail(username);
        if (userEntity.isPresent()) {
            return new UserDetailDTO(userEntity.get());
        }
        throw new SilentApplicationErrorException(ApplicationErrorType.UNKNOWN_USER);
    }
}
