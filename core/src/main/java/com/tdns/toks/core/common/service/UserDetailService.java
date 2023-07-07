package com.tdns.toks.core.common.service;

import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import com.tdns.toks.core.domain.user.model.entity.User;
import com.tdns.toks.core.domain.user.repository.UserRepository;
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
        throw new ApplicationErrorException(ApplicationErrorType.UNKNOWN_USER);
    }
}
