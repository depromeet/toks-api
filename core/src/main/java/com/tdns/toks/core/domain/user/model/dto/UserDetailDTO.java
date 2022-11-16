package com.tdns.toks.core.domain.user.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tdns.toks.core.common.type.JwtToken;
import com.tdns.toks.core.domain.user.model.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@Builder
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "UserDetailDto", description = "User 디테일 정보")
public class UserDetailDTO implements UserDetails {

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
        return user.getUpwd();
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
