package com.tdns.toks.api.domain.quiz.model.dto;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import com.tdns.toks.core.domain.quiz.model.dto.QuizSimpleDTO;
import com.tdns.toks.core.domain.quiz.type.QuizStatusType;
import com.tdns.toks.core.domain.quiz.type.QuizType;
import com.tdns.toks.core.domain.user.model.dto.UserSimpleDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class QuizApiDTO {
	@Getter
	@RequiredArgsConstructor
	@Schema(name = "QuizSimpleResponse", description = "QUIZ 단일 조회 응답 모델")
	public static class QuizSimpleResponse {
		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quiz id", description = "quiz id")
		private final Long quizId;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quiz", description = "quiz")
		private final String quiz;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quiz type", description = "quiz type")
		private final QuizType quizType;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "description", description = "description")
		private final String description;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "started at", description = "started at")
		private final LocalDateTime startedAt;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "ended at", description = "ended at")
		private final LocalDateTime endedAt;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "duration of second", description = "duration of second")
		private final Long durationOfSecond;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "timestamp", description = "timestamp")
		private final Timestamp timestamp;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "image urls", description = "image urls")
		private final List<String> imageUrls;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "creator", description = "creator")
		private final UserSimpleDTO creator;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "study id", description = "study id")
		private final Long studyId;

		public static QuizSimpleResponse toResponse(QuizSimpleDTO quizSimpleDTO) {
			return new QuizSimpleResponse(
				quizSimpleDTO.getQuizId(),
				quizSimpleDTO.getQuiz(),
				quizSimpleDTO.getQuizType(),
				quizSimpleDTO.getDescription(),
				quizSimpleDTO.getStartedAt(),
				quizSimpleDTO.getEndedAt(),
				Duration.between(quizSimpleDTO.getStartedAt(), quizSimpleDTO.getEndedAt()).getSeconds(),
				new Timestamp(System.currentTimeMillis()),
				quizSimpleDTO.getImageUrls(),
				quizSimpleDTO.getCreator(),
				quizSimpleDTO.getStudyId()
			);
		}
	}

	@Getter
	@RequiredArgsConstructor
	@Schema(name = "QuizResponse", description = "QUIZ 조회 응답 모델")
	public static class QuizResponse {
		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quiz id", description = "quiz id")
		private final Long quizId;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quiz", description = "quiz")
		private final String quiz;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quiz type", description = "quiz type")
		private final QuizType quizType;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "description", description = "description")
		private final String description;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "started at", description = "started at")
		private final LocalDateTime startedAt;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "ended at", description = "ended at")
		private final LocalDateTime endedAt;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "duration of second", description = "duration of second")
		private final Long durationOfSecond;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "timestamp", description = "timestamp")
		private final Timestamp timestamp;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "creator", description = "creator")
		private final UserSimpleDTO creator;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "unSubmitters", description = "unSubmitters")
		private final List<UserSimpleDTO> unSubmitters;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "study id", description = "study id")
		private final Long studyId;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "study id", description = "study id")
		private final QuizStatusType quizStatus;

		public static QuizResponse toResponse(QuizSimpleDTO quizSimpleDTO, List<UserSimpleDTO> unSubmitter, QuizStatusType quizStatus) {
			return new QuizResponse(
				quizSimpleDTO.getQuizId(),
				quizSimpleDTO.getQuiz(),
				quizSimpleDTO.getQuizType(),
				quizSimpleDTO.getDescription(),
				quizSimpleDTO.getStartedAt(),
				quizSimpleDTO.getEndedAt(),
				Duration.between(quizSimpleDTO.getStartedAt(), quizSimpleDTO.getEndedAt()).getSeconds(),
				new Timestamp(System.currentTimeMillis()),
				quizSimpleDTO.getCreator(),
				unSubmitter,
				quizSimpleDTO.getStudyId(),
				quizStatus
			);
		}
	}

	@Getter
	@RequiredArgsConstructor
	@Schema(name = "QuizResponse", description = "QUIZ 조회 응답 모델")
	public static class QuizzesResponse {
		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quizzes response", description = "quizzes response")
		private final List<QuizResponse> quizzes;
	}
}
