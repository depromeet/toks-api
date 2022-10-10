package com.tdns.toks.api.domain.user.service;

import com.tdns.toks.core.common.type.JwtToken;
import com.tdns.toks.core.config.security.JwtTokenProvider;
import com.tdns.toks.core.domain.user.model.entity.UserEntity;
import com.tdns.toks.core.domain.user.service.UserService;
import com.tdns.toks.core.domain.user.type.UserRole;
import com.tdns.toks.core.domain.user.type.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserLoginRequest;

@Service
@RequiredArgsConstructor
public class UserApiService {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JwtToken login(UserLoginRequest userLoginRequest) {
        UserEntity userEntity = userService.getUser(userLoginRequest.getProvider(), userLoginRequest.getProviderId());
        if (userEntity == null) {
            userEntity = userService.createUser(convertToUserEntity(userLoginRequest));
        }
        return (jwtTokenProvider.generateToken(userEntity.getEmail()));
    }

    private UserEntity convertToUserEntity(UserLoginRequest userLoginRequest){
        return UserEntity
                .builder()
                .email(userLoginRequest.getEmail())
                .name("random")
                .status(UserStatus.ACTIVE)
                .userRole(UserRole.USER)
                .upwd(bCryptPasswordEncoder.encode(userLoginRequest.getEmail()))
                .provider(userLoginRequest.getProvider())
                .providerId(userLoginRequest.getProviderId())
                .build();
    }
}
