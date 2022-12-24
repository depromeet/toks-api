package com.tdns.toks.core.domain.study.model.entity;

import com.tdns.toks.core.common.model.entity.BaseTimeEntity;
import com.tdns.toks.core.domain.study.type.StudyCapacity;
import com.tdns.toks.core.domain.study.type.StudyStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "study")
public class Study extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '스터디 이름'")
    private String name;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '스터디 설명'")
    private String description;

    @Column(columnDefinition = "DATETIME COMMENT '시작 일자'")
    private LocalDateTime startedAt;

    @Column(columnDefinition = "DATETIME COMMENT '종료 일자'")
    private LocalDateTime endedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) COMMENT '스터디 상태'")
    private StudyStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10) COMMENT '스터디 인원'")
    private StudyCapacity capacity;

    @Column(nullable = false, columnDefinition = "TINYINT COMMENT '스터디 참여 중인 인원'")
    private Integer studyUserCount;

    @Column(columnDefinition = "BIGINT COMMENT '스터디장 user_id'")
    private Long leaderId;

    public void updateStudyUserCount(int count) {
        this.studyUserCount += count;
    }
}
