package com.tdns.toks.core.domain.model.entity.quiz;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.tdns.toks.core.domain.model.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class QuizAnswer extends BaseEntity {

	// TODO: 정답 포맷 논의
	@Column(nullable = false, columnDefinition = "JSON COMMENT '정답'")
	private String answer;
}
