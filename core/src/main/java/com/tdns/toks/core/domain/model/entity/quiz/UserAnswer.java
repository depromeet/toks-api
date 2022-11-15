package com.tdns.toks.core.domain.model.entity.quiz;

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
public class UserAnswer extends BaseEntity {

	// TODO: 답변 포맷 논의
	@Column(nullable = false, columnDefinition = "JSON COMMENT '정답'")
	private String answer;

	@CreatedBy
	@Column(nullable = false, columnDefinition = "BIGINT COMMENT '생성자'")
	private Long createdBy;
}
