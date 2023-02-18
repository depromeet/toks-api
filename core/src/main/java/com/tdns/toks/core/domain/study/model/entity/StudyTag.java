package com.tdns.toks.core.domain.study.model.entity;

import com.tdns.toks.core.common.model.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "study_tag")
public class StudyTag extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT COMMENT '스터디 태그 번호'")
    private Long id;

    @Column(name = "study_id", columnDefinition = "BIGINT COMMENT '스터디 번호'")
    private Long studyId;

    @Column(name = "tag_id", columnDefinition = "BIGINT COMMENT '태그 번호'")
    private Long tagId;

    public StudyTag(Long studyId, Long id) {
        this.studyId = studyId;
        this.tagId = id;
    }
}
