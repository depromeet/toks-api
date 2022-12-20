package com.tdns.toks.core.domain.quiz.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tdns.toks.core.common.model.entity.BaseTimeEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class QuizReplyHistory extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, columnDefinition = "TEXT COMMENT '정답'")
	private String answer;

	@Column(nullable = false, columnDefinition = "BIGINT COMMENT '퀴즈 id'")
	private Long quizId;

	@CreatedBy
	@Column(nullable = false, columnDefinition = "BIGINT COMMENT '생성자'")
	private Long createdBy;

	public static QuizReplyHistory of(
		final String answer,
		final Long quizId)
	{
		return new QuizReplyHistory(answer, quizId);
	}

	public QuizReplyHistory(
		final String answer,
		final Long quizId
	) {
		this.answer = answer;
		this.quizId = quizId;
	}
}
