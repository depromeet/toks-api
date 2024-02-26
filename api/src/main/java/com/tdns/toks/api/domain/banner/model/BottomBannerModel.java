package com.tdns.toks.api.domain.banner.model;

import com.tdns.toks.core.domain.banner.entity.BottomBanner;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BottomBannerModel {
    private final Long id;
    private final String title;
    private final int seq;
    private final String imageUrl;
    private final String landingUrl;
    private final boolean isActive;

    public static BottomBannerModel from(BottomBanner bottomBanner) {
        return new BottomBannerModel(
                bottomBanner.getId(),
                bottomBanner.getTitle(),
                bottomBanner.getSeq(),
                bottomBanner.getImageUrl(),
                bottomBanner.getLandingUrl(),
                bottomBanner.isActive()
        );
    }
}
