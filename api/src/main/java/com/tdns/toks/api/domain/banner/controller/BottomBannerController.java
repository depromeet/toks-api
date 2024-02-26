package com.tdns.toks.api.domain.banner.controller;

import com.tdns.toks.api.domain.banner.model.dto.BottomBannerResponse;
import com.tdns.toks.api.domain.banner.service.BottomBannerService;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "바텀 배너", description = "Bottom Banner API")
@RestController
@RequestMapping(path = "/api/v1/bottom-banners", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BottomBannerController {
    private final BottomBannerService bottomBannerService;

    @Operation(summary = "바텀 배너 조회")
    @GetMapping
    public ResponseEntity<ResponseDto<BottomBannerResponse.GetAllBottomBannerResponse>> getAllBottomBanners(
            @Nullable AuthUser authUser
    ) {
        var response = bottomBannerService.getAllBottomBanners(authUser);
        return ResponseDto.ok(response);
    }
}
