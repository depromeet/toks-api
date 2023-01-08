package com.tdns.toks.core.domain.image.model.entity;

import com.tdns.toks.core.common.model.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "image")
public class Image extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(512) COMMENT '퀴즈 url'")
    private String imageUrl;

    @Column(columnDefinition = "BIGINT COMMENT '생성 user_id'")
    private Long createdBy;
}
