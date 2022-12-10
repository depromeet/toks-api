package com.tdns.toks.core.domain.quizrank.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuizRankDTO {

	private Long quizRankId;
	private Integer score;
	private UserSimpleDTO user;

	@Getter
	@NoArgsConstructor
	public static class UserSimpleDTO {
		private Long userId;
		private String nickname;
		private String profileImageUrl;
	}
}
