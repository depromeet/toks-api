package com.tdns.toks.api.domain.admin.dto;

import com.tdns.toks.core.domain.banner.entity.BottomBanner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminBottomBannerResponse {
    private Long id;
    private String title;
    private int seq;
    private String imageUrl;
    private String landingUrl;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static AdminBottomBannerResponse from(BottomBanner bottomBanner) {
        return new AdminBottomBannerResponse(
                bottomBanner.getId(),
                bottomBanner.getTitle(),
                bottomBanner.getSeq(),
                bottomBanner.getImageUrl(),
                bottomBanner.getLandingUrl(),
                bottomBanner.isActive(),
                bottomBanner.getCreatedAt(),
                bottomBanner.getUpdatedAt()
        );
    }
}
