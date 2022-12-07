package com.tdns.toks.api.domain.quiz.model.dto;

import javax.validation.constraints.NotEmpty;

import com.tdns.toks.core.domain.quiz.model.entity.QuizLike;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class QuizLikeApiDTO {
	@Builder
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(name = "QuizLikeRequest", description = "USER QUIZ LIKE 생성 요청 모델")
	public static class QuizLikeRequest {
		@NotEmpty(message = "퀴즈 답변 id는 필수 항목입니다.")
		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "user quiz history id", description = "user quiz history id")
		private Long userQuizHistoryId;
	}

	@Builder
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(name = "QuizLikeResponse", description = "USER QUIZ HISTORY 생성 응답 모델")
	public static class QuizLikeResponse {
		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "user quiz like id", description = "user quiz like id")
		private Long quizLikeId;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "user quiz history id", description = "user quiz history id")
		private Long userQuizHistoryId;

		public static QuizLikeResponse toResponse(QuizLike quizLike) {
			return new QuizLikeResponse(
				quizLike.getId(),
				quizLike.getUserQuizHistoryId()
			);
		}
	}
}
