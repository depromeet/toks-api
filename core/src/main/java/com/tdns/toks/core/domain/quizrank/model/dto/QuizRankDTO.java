package com.tdns.toks.core.domain.quizrank.model.dto;

import com.tdns.toks.core.domain.user.model.dto.UserSimpleDTO;

import lombok.Getter;

@Getter
public class QuizRankDTO {

	private Long quizRankId;
	private Integer score;
	private UserSimpleDTO user;
}
