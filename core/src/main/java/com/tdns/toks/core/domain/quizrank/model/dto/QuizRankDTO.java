package com.tdns.toks.core.domain.quizrank.model.dto;

import com.tdns.toks.core.domain.user.model.dto.UserSimpleDTO;

import lombok.Getter;

@Getter
public class QuizRankDTO {

	private Integer score;
	private Integer rank;
	private UserSimpleDTO user;

	public void updateRank(Integer rank) {
		this.rank = rank;
	}
}
