package com.tdns.toks.core.domain.user.model.dto;

import com.tdns.toks.core.domain.user.model.entity.User;
import lombok.Getter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserSimpleDTO {
	private Long userId;
	private String nickname;
	private String profileImageUrl;

	public static UserSimpleDTO toDto(User user) {
		return UserSimpleDTO.builder()
				.userId(user.getId())
				.nickname(user.getNickname())
				.profileImageUrl(user.getProfileImageUrl())
				.build();
	}
}
