package com.tdns.toks.api.domain.quiz.model.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.tdns.toks.core.domain.quiz.model.dto.QuizReplyHistoryDto;
import com.tdns.toks.core.domain.quiz.model.entity.QuizReplyHistory;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class QuizReplyHistoryApiDTO {
	@Getter
	@Schema(name = "QuizReplyHistoryRequest", description = "QUIZ REPLY HISTORY 생성 요청 모델")
	public static class QuizReplyHistoryRequest {
		@NotEmpty(message = "퀴즈 답변은 필수 항목입니다.")
		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "answer", description = "댭변")
		private String answer;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quiz id", description = "quiz id")
		private Long quizId;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(name = "QuizReplyHistoryRequest", description = "QUIZ REPLY HISTORY 생성 응답 모델")
	public static class QuizReplyHistoryResponse {
		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quiz reply history id", description = "quiz reply history id")
		private Long quizReplyHistoryId;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "answer", description = "댭변")
		private String answer;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quiz id", description = "quiz id")
		private Long quizId;

		public static QuizReplyHistoryResponse toResponse(QuizReplyHistory quizReplyHistory) {
			return new QuizReplyHistoryResponse(
				quizReplyHistory.getId(),
				quizReplyHistory.getAnswer(),
				quizReplyHistory.getQuizId()
			);
		}
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(name = "QuizReplyHistoriesResponse", description = "QUIZ REPLY HISTORY 생성 응답 모델")
	public static class QuizReplyHistoriesResponse {
		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quiz reply history 리스트", description = "quiz reply history 리스트")
		private List<QuizReplyHistoryDto> quizReplyHistories;
	}
}
