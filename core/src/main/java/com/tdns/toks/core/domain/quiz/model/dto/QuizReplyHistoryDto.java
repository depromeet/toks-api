package com.tdns.toks.core.domain.quiz.model.dto;

import com.tdns.toks.core.domain.user.model.dto.UserSimpleDTO;

import lombok.Getter;

@Getter
public class QuizReplyHistoryDto {

	private Long quizReplyHistoryId;
	private String answer;
	private Long likeNumber;
	private UserSimpleDTO creator;
}
