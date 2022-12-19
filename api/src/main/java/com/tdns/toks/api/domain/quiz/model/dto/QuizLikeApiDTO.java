package com.tdns.toks.api.domain.quiz.model.dto;

import javax.validation.constraints.NotNull;

import com.tdns.toks.core.domain.quiz.model.entity.QuizLike;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class QuizLikeApiDTO {
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(name = "QuizLikeRequest", description = "QUIZ LIKE 생성 요청 모델")
	public static class QuizLikeRequest {
		@NotNull(message = "퀴즈 답변 id는 필수 항목입니다.")
		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quizReplyHistoryId", description = "quiz reply history id")
		private Long quizReplyHistoryId;
	}

	@Getter
	@RequiredArgsConstructor
	@Schema(name = "QuizLikeResponse", description = "QUIZ LIKE 생성 응답 모델")
	public static class QuizLikeResponse {
		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quizLikeId", description = "user quiz like id")
		private final Long quizLikeId;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quizReplyHistoryId", description = "quiz reply history id")
		private final Long quizReplyHistoryId;

		public static QuizLikeResponse toResponse(QuizLike quizLike) {
			return new QuizLikeResponse(
				quizLike.getId(),
				quizLike.getQuizReplyHistoryId()
			);
		}
	}
}
