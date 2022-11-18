package com.tdns.toks.core.domain.quiz.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedBy;

import com.tdns.toks.core.common.model.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserQuizHistory extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// TODO: 답변 포맷 논의
	@Column(nullable = false, columnDefinition = "JSON COMMENT '정답'")
	private String answer;

	@CreatedBy
	@Column(nullable = false, columnDefinition = "BIGINT COMMENT '생성자'")
	private Long createdBy;
}
