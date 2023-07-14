package com.tdns.toks.core.domain.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserSimpleDTO {
    private Long userId;
    private String nickname;
    private String profileImageUrl;
}
