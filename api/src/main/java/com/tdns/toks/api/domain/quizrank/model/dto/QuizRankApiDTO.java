package com.tdns.toks.api.domain.quizrank.model.dto;

import java.util.List;

import com.tdns.toks.core.domain.quizrank.model.dto.QuizRankDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class QuizRankApiDTO {
	@Getter
	@RequiredArgsConstructor
	@Schema(name = "QuizRanksApiResponse", description = "Ranks 응답 모델")
	public static class QuizRanksApiResponse {
		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "ranks", description = "rank 리스트")
		private final List<QuizRankDTO> quizRanksDto;
	}
}
