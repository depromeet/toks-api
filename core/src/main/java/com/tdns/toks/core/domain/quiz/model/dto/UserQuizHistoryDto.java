package com.tdns.toks.core.domain.quiz.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserQuizHistoryDto {

	private Long userQuizHistoryId;
	private String answer;
	private Long likeNumber;
	private UserSimpleDTO creator;

	@Getter
	@NoArgsConstructor
	public static class UserSimpleDTO {
		private Long userId;
		private String nickname;
		private String profileImageUrl;
	}
}
