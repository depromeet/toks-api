package com.tdns.toks.core.domain.study.model.entity;

import com.tdns.toks.core.common.model.entity.BaseTimeEntity;
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
@Table(name = "tag")
public class Tag extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "태그 번호")
    private Long id;

    @Column(name = "name", columnDefinition = "태그 이름")
    private String name;
}
