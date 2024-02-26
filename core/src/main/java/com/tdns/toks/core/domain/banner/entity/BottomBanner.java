package com.tdns.toks.core.domain.banner.entity;

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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bottom banner
 */
@Table(name = "bottom_banner")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class BottomBanner extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "seq")
    private int seq;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "landing_url")
    private String landingUrl;

    @Column(name = "is_active")
    private Boolean isActive;

    public void update(String title, int seq, String imageUrl, String landingUrl, Boolean isActive) {
        this.title = title;
        this.seq = seq;
        this.imageUrl = imageUrl;
        this.landingUrl = landingUrl;
        this.isActive = isActive;
    }
}
