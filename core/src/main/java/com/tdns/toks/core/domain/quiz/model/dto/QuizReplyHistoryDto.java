package com.tdns.toks.core.domain.quiz.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class QuizReplyHistoryDto {

	private final Long quizReplyHistoryId;
	private final String answer;
	private final Long likeNumber;
	private final UserSimpleDTO creator;

	@Getter
	@RequiredArgsConstructor
	public static class UserSimpleDTO {
		private final Long userId;
		private final String nickname;
		private final String profileImageUrl;
	}
}
