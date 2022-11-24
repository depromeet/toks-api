package com.tdns.toks.core.domain.study.model.entity;

import com.tdns.toks.core.common.model.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "study_tag")
public class StudyTag extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "스터디 태그 번호")
    private Long id;

    @Column(name = "study_id", columnDefinition = "스터디 번호")
    private Long studyId;

    @Column(name = "tag_id", columnDefinition = "태그 번호")
    private Long tagId;

}
