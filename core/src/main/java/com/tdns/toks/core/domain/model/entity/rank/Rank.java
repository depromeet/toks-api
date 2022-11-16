package com.tdns.toks.core.domain.model.entity.rank;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.tdns.toks.core.common.model.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Rank extends BaseEntity {

	@Column(nullable = false, columnDefinition = "BIGINT COMMENT '사용자 id'")
	private Long userId;

	@Column(nullable = false, columnDefinition = "BIGINT COMMENT '스터디 id'")
	private Long studyId;

	@Column(nullable = false, columnDefinition = "TINYINT COMMENT '스터디 id'")
	private Integer baseAnswerCount;
}
