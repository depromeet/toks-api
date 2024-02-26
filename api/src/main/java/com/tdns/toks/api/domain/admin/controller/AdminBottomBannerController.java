package com.tdns.toks.api.domain.admin.controller;

import com.tdns.toks.api.domain.admin.dto.AdminBottomBannerResponse;
import com.tdns.toks.api.domain.admin.dto.AdminBottomBannerSaveOrUpdateRequest;
import com.tdns.toks.api.domain.admin.service.AdminBottomBannerService;
import com.tdns.toks.core.common.model.dto.PageableResponseDto;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import com.tdns.toks.core.domain.auth.annotation.AdminPermission;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "[어드민] 바텀 배너 관리", description = "BOTTOM BANNER API")
@RestController
@RequestMapping(path = "/api/v1/admin/bottom-banners", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AdminBottomBannerController {
    private final AdminBottomBannerService adminBottomBannerService;

    @Operation(summary = "바텀 배너 단건 조회")
    @AdminPermission
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<AdminBottomBannerResponse>> get(
            AuthUser authUser,
            @PathVariable Long id
    ) {
        var response = adminBottomBannerService.get(authUser, id);
        return ResponseDto.ok(response);
    }

    @Operation(summary = "바텀 배너 페이징 조회")
    @AdminPermission
    @GetMapping
    public ResponseEntity<ResponseDto<PageableResponseDto<AdminBottomBannerResponse>>> getAll(
            AuthUser authUser,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size
    ) {
        var response = adminBottomBannerService.getAll(authUser, page, size);
        return PageableResponseDto.ok(response);
    }

    @Operation(summary = "바텀 배너 삽입")
    @AdminPermission
    @PostMapping
    public ResponseEntity<ResponseDto<AdminBottomBannerResponse>> create(
            AuthUser authUser,
            @RequestBody AdminBottomBannerSaveOrUpdateRequest request
    ) {
        var response = adminBottomBannerService.create(authUser, request);
        return ResponseDto.ok(response);
    }

    @Operation(summary = "바텀 배너 수정")
    @AdminPermission
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<AdminBottomBannerResponse>> update(
            AuthUser authUser,
            @PathVariable Long id,
            @RequestBody AdminBottomBannerSaveOrUpdateRequest request
    ) {
        var response = adminBottomBannerService.update(authUser, id, request);
        return ResponseDto.ok(response);
    }

    @Operation(summary = "바텀 배너 삭제")
    @AdminPermission
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            AuthUser authUser,
            @PathVariable Long id
    ) {
        adminBottomBannerService.delete(authUser, id);
        return ResponseDto.noContent();
    }
}
