package com.tdns.toks.core.domain.model.entity.quiz;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.data.annotation.CreatedBy;

import com.tdns.toks.core.domain.model.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Quiz extends BaseEntity {

	@Column(nullable = false, columnDefinition = "VARCHAR(255) COMMENT '퀴즈'")
	private String quiz;

	@Column(columnDefinition = "VARCHAR(255) COMMENT '퀴즈 설명'")
	private String description;

	@Column(nullable = false, columnDefinition = "VARCHAR(10) COMMENT '퀴즈 타입'")
	private QuizType quizType;

	@Column(nullable = false, columnDefinition = "DATETIME COMMENT '시작시간'")
	private LocalDateTime startedAt;

	@Column(nullable = false, columnDefinition = "DATETIME COMMENT '종료시간'")
	private LocalDateTime endedAt;

	@Column(columnDefinition = "VARCHAR(255) COMMENT '이미지 url'")
	private String imageUrl;

	@Column(columnDefinition = "TINYINT COMMENT '난이도'")
	private Integer difficulty;

	@Column(nullable = false, columnDefinition = "BIGINT COMMENT '스터디 id'")
	private Long studyId;

	@Column(nullable = false, columnDefinition = "BIGINT COMMENT '정답 id'")
	private Long quizAnswerId;

	@CreatedBy
	@Column(nullable = false, columnDefinition = "BIGINT COMMENT '생성자'")
	private Long createdBy;
}
