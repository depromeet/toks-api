package com.tdns.toks.core.domain.user.model.dto;

import com.tdns.toks.core.domain.user.model.entity.UserEntity;
import com.tdns.toks.core.domain.user.type.UserProvider;
import com.tdns.toks.core.domain.user.type.UserRole;
import com.tdns.toks.core.domain.user.type.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private UserStatus status;
    private UserRole userRole;
    private UserProvider provider;
    private String providerId;

    public static UserDTO convertEntityToDTO(UserEntity userEntity){
        UserDTO userDTO = new UserDTO();
        userDTO.id = userEntity.getId();
        userDTO.email = userEntity.getEmail();
        userDTO.name = userEntity.getName();
        userDTO.status = userEntity.getStatus();
        userDTO.userRole = userEntity.getUserRole();
        userDTO.provider = userEntity.getProvider();
        userDTO.providerId = userEntity.getProviderId();
        return userDTO;
    }
}
