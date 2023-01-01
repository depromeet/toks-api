package com.tdns.toks.core.domain.quartz.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tdns.toks.core.common.model.entity.BaseTimeEntity;
import com.tdns.toks.core.domain.quartz.type.JobType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"type"}, name = "job_uk_type"),
        @UniqueConstraint(columnNames = {"name"}, name = "job_uk_name"),
})
public class Job extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(40) NOT NULL COMMENT 'Job 유형'")
    private JobType type;

    @Column(columnDefinition = "varchar(128) NOT NULL COMMENT 'Job 이름'")
    private String name;

    @Column(columnDefinition = "varchar(32) NOT NULL COMMENT 'Job cron'")
    private String cronString;

    @Column(columnDefinition = "varchar(255) NULL COMMENT 'Job cron 설명'")
    private String cronDescription;

    @Builder.Default
    @Column(columnDefinition = "bit DEFAULT b'0' NOT NULL COMMENT 'Job 활성화 유무'")
    private Boolean activated = false;

    @Column(length = 65535, columnDefinition = "Text NULL COMMENT 'Job 실행 Param'")
    private String params;

    @Builder.Default
    @Column(columnDefinition = "bit DEFAULT b'0' NOT NULL COMMENT '삭제여부'")
    private Boolean deleted = false;

    @Column(columnDefinition = "varchar(32) NULL COMMENT 'Job을 관리하는 서비스명'")
    private String service;

    private LocalDateTime nextFireAt;
}
