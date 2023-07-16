package com.tdns.toks.core.domain.quizrank.entity;

import com.tdns.toks.core.common.model.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * TODO : 현재 미사용중인 테이블
 * - 추후 삭제 처리 가능성 존재
 */
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
}
