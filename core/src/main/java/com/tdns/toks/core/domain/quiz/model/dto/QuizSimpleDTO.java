package com.tdns.toks.core.domain.quiz.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.tdns.toks.core.domain.quiz.type.QuizType;
import com.tdns.toks.core.domain.user.model.dto.UserSimpleDTO;

import lombok.Getter;

@Getter
public class QuizSimpleDTO {
	private Long quizId;

	private String question;

	private QuizType quizType;

	private String description;

	private LocalDateTime startedAt;

	private LocalDateTime endedAt;

	private List<String> imageUrls;

	private UserSimpleDTO creator;

	private Long studyId;

	private Integer round;
}
