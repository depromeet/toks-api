package com.tdns.toks.api.domain.quiz.model.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import com.tdns.toks.core.domain.quiz.type.QuizType;
import com.tdns.toks.core.domain.study.type.StudyCapacity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class QuizApiDTO {
	@Builder
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(name = "QuizCreateRequest", description = "QUIZ 생성 요청 모델")
	public static class QuizCreateRequest {
		@NotEmpty(message = "스터디 이름은 필수 항목 입니다.")
		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quiz", description = "퀴즈")
		private String quiz;

		@NotEmpty(message = "스터디 설명은 필수 항목 입니다.")
		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "description", description = "설명설명")
		private QuizType quizType;

		@NotEmpty(message = "스터디 설명은 필수 항목 입니다.")
		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "description", description = "설명설명")
		private String description;

		@NotEmpty(message = "스터디 설명은 필수 항목 입니다.")
		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "description", description = "설명설명")
		private String answer;

		@NotEmpty(message = "종료 일자는 필수 항목 입니다.")
		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "endDate", description = "종료 일자 yyyy-MM-dd")
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDate startedAt;

		@NotEmpty(message = "종료 일자는 필수 항목 입니다.")
		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "endDate", description = "종료 일자 yyyy-MM-dd")
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDate endedAt;

		@NotEmpty(message = "스터디 규모는 필수 항목 입니다.")
		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "capacity", description = "스터디 규모")
		private String imageUrl;

		@Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "tag id list", description = "태그 id list")
		private Long studyId;
	}
}
