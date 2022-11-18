package com.tdns.toks.core.domain.rank.model.entity;

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
public class Rank extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, columnDefinition = "BIGINT COMMENT '사용자 id'")
	private Long userId;

	@Column(nullable = false, columnDefinition = "BIGINT COMMENT '스터디 id'")
	private Long studyId;

	@Column(nullable = false, columnDefinition = "TINYINT COMMENT '스터디 id'")
	private Integer baseAnswerCount;
}
