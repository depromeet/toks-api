package com.tdns.toks.core.domain.quiz.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;

import com.tdns.toks.core.common.model.entity.BaseTimeEntity;
import com.tdns.toks.core.domain.quiz.type.QuizType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "quiz")
public class Quiz extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, columnDefinition = "VARCHAR(255) COMMENT '퀴즈'")
	private String quiz;

	@Column(columnDefinition = "VARCHAR(255) COMMENT '퀴즈 설명'")
	private String description;

	// TODO: 정답 포맷 논의
	@Column(nullable = false, columnDefinition = "JSON COMMENT '정답'")
	private String answer;

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
