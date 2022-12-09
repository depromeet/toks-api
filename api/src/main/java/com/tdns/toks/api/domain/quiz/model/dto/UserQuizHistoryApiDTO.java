package com.tdns.toks.api.domain.quiz.model.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.tdns.toks.core.domain.quiz.model.dto.UserQuizHistoryDto;
import com.tdns.toks.core.domain.quiz.model.entity.UserQuizHistory;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserQuizHistoryApiDTO {
	@Getter
	@Schema(name = "UserQuizHistoryRequest", description = "USER QUIZ HISTORY 생성 요청 모델")
	public static class UserQuizHistoryRequest {
		@NotEmpty(message = "퀴즈 답변은 필수 항목입니다.")
		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "answer", description = "댭변")
		private String answer;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quiz id", description = "quiz id")
		private Long quizId;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(name = "UserQuizHistoryRequest", description = "USER QUIZ HISTORY 생성 응답 모델")
	public static class UserQuizHistoryResponse {
		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "user quiz history id", description = "quiz id")
		private Long userQuizHistoryId;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "answer", description = "댭변")
		private String answer;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quiz id", description = "quiz id")
		private Long quizId;

		public static UserQuizHistoryResponse toResponse(UserQuizHistory userQuizHistory) {
			return new UserQuizHistoryResponse(
				userQuizHistory.getId(),
				userQuizHistory.getAnswer(),
				userQuizHistory.getQuizId()
			);
		}
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(name = "UserQuizHistoriesResponse", description = "USER QUIZ HISTORY 생성 응답 모델")
	public static class UserQuizHistoriesResponse {
		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "user quiz history 리스트", description = "user quiz history 리스트")
		private List<UserQuizHistoryDto> userQuizHistories;
	}
}
