package com.tdns.toks.core.domain.user.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class UserSimpleDTO {
	private Long userId;
	private String nickname;
	private String profileImageUrl;
}
