package com.tdns.toks.core.domain.category.entity;

import com.tdns.toks.core.common.model.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 고정 데이터, 별도의 생성로직은 sql로 인서트만
 */
@Table(name = "category")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Category extends BaseTimeEntity {
    @Id
    private String id;

    private Integer depth;

    private String name;

    private String description;

    @Column(name = "image_url")
    private String imageUrl;
}
