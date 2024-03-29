package com.tdns.toks.core.domain.tag.entity;

import com.tdns.toks.core.common.model.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tag")
public class Tag extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT COMMENT '태그 번호'")
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(20) COMMENT '태그 이름'")
    private String name;
}
