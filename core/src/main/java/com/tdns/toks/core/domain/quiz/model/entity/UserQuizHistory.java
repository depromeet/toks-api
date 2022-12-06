package com.tdns.toks.core.domain.quiz.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedBy;

import com.tdns.toks.core.common.model.entity.BaseTimeEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserQuizHistory extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// TODO: 답변 포맷 논의
	@Column(nullable = false, columnDefinition = "JSON COMMENT '정답'")
	private String answer;

	@Column(nullable = false, columnDefinition = "BIGINT COMMENT '퀴즈 id'")
	private Long quizId;

	@CreatedBy
	@Column(nullable = false, columnDefinition = "BIGINT COMMENT '생성자'")
	private Long createdBy;

	public static UserQuizHistory of(String answer, Long quizId) {
		return new UserQuizHistory(answer, quizId);
	}

	public UserQuizHistory(String answer, Long quizId) {
		this.answer = answer;
		this.quizId = quizId;
	}
}
