package com.tdns.toks.core.domain.quiz.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.data.annotation.CreatedBy;

import com.tdns.toks.core.common.model.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AnswerLike extends BaseEntity {

	@Column(nullable = false, columnDefinition = "BIGINT COMMENT '답변 id'")
	private Long userAnswerId;

	@CreatedBy
	@Column(nullable = false, columnDefinition = "BIGINT COMMENT '생성자'")
	private Long createdBy;
}
