package com.tdns.toks.core.domain.quiz.model.entity;

import com.tdns.toks.core.common.model.entity.BaseTimeEntity;
import com.tdns.toks.core.common.model.converter.StringArrayConverter;
import com.tdns.toks.core.domain.quiz.type.QuizType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Quiz extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name = "category_id")
    private String categoryId;

	@Column(nullable = false, columnDefinition = "VARCHAR(255) COMMENT '퀴즈'")
	private String question;

	@Column(nullable = false, columnDefinition = "VARCHAR(10) COMMENT '퀴즈 타입'")
	@Enumerated(EnumType.STRING)
	private QuizType quizType;

	@Column(columnDefinition = "VARCHAR(255) COMMENT '퀴즈 설명'")
	private String description;

	@Column(nullable = false, columnDefinition = "TEXT COMMENT '정답'")
	private String answer;

	@Column(nullable = false, columnDefinition = "DATETIME COMMENT '시작시간'")
	private LocalDateTime startedAt;

	@Column(nullable = false, columnDefinition = "DATETIME COMMENT '종료시간'")
	private LocalDateTime endedAt;

	@Column(columnDefinition = "VARCHAR(512) COMMENT '이미지 url 리스트'")
	@Convert(converter = StringArrayConverter.class)
	private List<String> imageUrls;

	@Column(nullable = false, columnDefinition = "BIGINT COMMENT '스터디 id'")
	private Long studyId;

	@Column(nullable = false, columnDefinition = "TINYINT COMMENT '퀴즈 회차'")
	private Integer round;

	@CreatedBy
	@Column(nullable = false, columnDefinition = "BIGINT COMMENT '생성자'")
	private Long createdBy;

	public static Quiz of(
		final String question,
		final QuizType quizType,
		final String description,
		final String answer,
		final LocalDateTime startedAt,
		final LocalDateTime endedAt,
		final List<String> imageUrls,
		final Long studyId,
		final Integer round
	) {
		return new Quiz(
			question,
			quizType,
			description,
			answer,
			startedAt,
			endedAt,
			imageUrls,
			studyId,
			round
		);
	}

	public Quiz(
		final String question,
		final QuizType quizType,
		final String description,
		final String answer,
		final LocalDateTime startedAt,
		final LocalDateTime endedAt,
		final List<String> imageUrls,
		final Long studyId,
		final Integer round
	) {
		this.question = question;
		this.quizType = quizType;
		this.description = description;
		this.answer = answer;
		this.startedAt = startedAt;
		this.endedAt = endedAt;
		this.imageUrls = imageUrls;
		this.studyId = studyId;
		this.round = round;
	}
}
