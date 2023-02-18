package com.tdns.toks.core.domain.image.model.entity;

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
@Table(name = "image")
public class Image extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(2047) COMMENT '이미지 URL'")
    private String imageUrl;

    // TODO : 생성하는 인원에 대한 정보가 없으면?
    // TODO : 서비스 업로드 혹은 기타 라는 정보가 삽입될 수 있도록 하기
    @Column(columnDefinition = "BIGINT COMMENT '생성 user_id'")
    private Long createdBy;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '이미지 저장 정보'")
    private String extra;
}
