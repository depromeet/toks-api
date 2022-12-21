package com.tdns.toks.core.domain.study.model.entity;

import com.tdns.toks.core.common.model.entity.BaseTimeEntity;
import com.tdns.toks.core.domain.study.type.StudyUserStatus;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "study_user")
public class StudyUser extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "스터디 유저 번호")
    private Long id;

    @Column(name = "user_id", columnDefinition = "유저 번호")
    private Long userId;

    @Column(name = "study_id", columnDefinition = "스터디 번호")
    private Long studyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "스터디 유저 상태")
    private StudyUserStatus status;
}
