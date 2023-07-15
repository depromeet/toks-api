package com.tdns.toks.core.domain.quiz.entity;

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
public class QuizLike extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, columnDefinition = "BIGINT COMMENT '답변 id'")
	private Long quizReplyHistoryId;

	@CreatedBy
	@Column(nullable = false, columnDefinition = "BIGINT COMMENT '생성자'")
	private Long createdBy;

	public static QuizLike from(
		final Long quizReplyHistoryId
	) {
		return new QuizLike(quizReplyHistoryId);
	}

	public QuizLike(
		final Long quizReplyHistoryId
	) {
		this.quizReplyHistoryId = quizReplyHistoryId;
	}
}
