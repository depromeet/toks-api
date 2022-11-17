package com.tdns.toks.core.domain.user.model.dto;

import com.tdns.toks.core.common.type.JwtToken;
import com.tdns.toks.core.domain.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@Getter
@Schema(name = "UserDetailDto", description = "User 디테일 정보")
public class UserDetailDTO implements UserDetails, OAuth2User {

    private final User user;

    private JwtToken jwtToken;

    private Map<String, Object> attributes;

    public UserDetailDTO(User user) {
        this.user = user;
    }

    public UserDetailDTO(User user, Map<String, Object> attributes, JwtToken jwtToken) {
        this.user = user;
        this.attributes = attributes;
        this.jwtToken = jwtToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        //필수 오버라이드 항목이나 미사용.
        return null;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        //필수 오버라이드 항목이나 미사용.
        return null;
    }

    @Override
    public Map<String, Object> getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    public static UserDTO get() {
        UserDTO userDTO = null;
        try {
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                userDTO = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }
        } catch (Throwable e) {
            return new UserDTO();
        }

        return userDTO;
    }
}
