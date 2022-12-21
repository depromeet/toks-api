package com.tdns.toks.core.domain.quizrank.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.tdns.toks.core.common.model.entity.BaseTimeEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class QuizRank extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, columnDefinition = "TINYINT COMMENT '점수'")
	private Integer score;

	@Column(nullable = false, columnDefinition = "BIGINT COMMENT '사용자 id'")
	private Long userId;

	@Column(nullable = false, columnDefinition = "BIGINT COMMENT '스터디 id'")
	private Long studyId;

	public static QuizRank of(
		final Integer score,
		final Long userId,
		final Long studyId
	) {
		return new QuizRank(score, userId, studyId);
	}

	public QuizRank(
		final Integer score,
		final Long userId,
		final Long studyId
	) {
		this.score = score;
		this.userId = userId;
		this.studyId = studyId;
	}

	public void plusScore() {
		this.score++;
	}
}
