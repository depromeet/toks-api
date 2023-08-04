package com.tdns.toks.core.domain.user.entity;

import com.tdns.toks.core.common.model.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Table(name = "user_activity_count")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class UserActivityCount extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Integer totalVisitCount;
    private Integer totalSolveCount;

    public void updateTotalSolveCount() {
        this.totalSolveCount += 1;
    }

    public void updateTotalVisitCount() {
        this.totalVisitCount += 1;
    }
}
