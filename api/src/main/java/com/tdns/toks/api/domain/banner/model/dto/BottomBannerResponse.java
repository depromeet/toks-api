package com.tdns.toks.api.domain.banner.model.dto;

import com.tdns.toks.api.domain.banner.model.BottomBannerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class BottomBannerResponse {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetAllBottomBannerResponse {
        private List<BottomBannerModel> bottomBanners;
    }
}
