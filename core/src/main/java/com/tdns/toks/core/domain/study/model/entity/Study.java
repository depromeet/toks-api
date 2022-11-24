package com.tdns.toks.core.domain.study.model.entity;

import com.tdns.toks.core.common.model.entity.BaseTimeEntity;
import com.tdns.toks.core.domain.study.type.StudyCapacity;
import com.tdns.toks.core.domain.study.type.StudyStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "study")
public class Study extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "스터디 번호")
    private Long id;

    @Column(name = "name", columnDefinition = "스터디 이름")
    private String name;

    @Column(name = "description", columnDefinition = "스터디 설명")
    private String description;

    @Column(name = "start_date", columnDefinition = "시작 일자")
    private LocalDate startDate;

    @Column(name = "end_date", columnDefinition = "종료 일자")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "스터디 상태")
    private StudyStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "capacity", nullable = false, columnDefinition = "스터디 규모")
    private StudyCapacity capacity;

    @Column(name = "study_user_count", nullable = false, columnDefinition = "스터디 참여 중인 인원")
    private Integer studyUserCount;

    @Column(name = "leader_id", columnDefinition = "스터디장 user_id")
    private Long leaderId;
}
