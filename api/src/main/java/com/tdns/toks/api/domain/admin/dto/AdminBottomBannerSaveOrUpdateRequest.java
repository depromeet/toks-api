package com.tdns.toks.api.domain.admin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminBottomBannerSaveOrUpdateRequest {
    private String title;
    private int seq;
    private String imageUrl;
    private String landingUrl;
    private boolean isActive;
}
