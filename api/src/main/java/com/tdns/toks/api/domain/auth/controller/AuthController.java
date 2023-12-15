package com.tdns.toks.api.domain.auth.controller;

import com.tdns.toks.api.domain.auth.service.AuthService;
import com.tdns.toks.api.domain.user.model.dto.UserApiDTO;
import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserRenewAccessTokenRequest;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import com.tdns.toks.core.domain.auth.AuthUserValidator;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;

@Tag(name = "인증인가 관리", description = "Auth")
@RestController
@RequestMapping(path = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "인증 사용자 본인 정보 조회")
    @GetMapping("/my-infos")
    public ResponseEntity<ResponseDto<UserApiDTO.UserInfoResponse>> getMyInfos(AuthUser authUser) {
        var response = authService.getMyInfos(authUser);
        return ResponseDto.ok(response);
    }

    @Operation(summary = "사용자 accessToken 갱신 요청")
    @PostMapping("/renew")
    public ResponseEntity<ResponseDto<UserApiDTO.UserRenewAccessTokenResponse>> renewAccessToken(
            @RequestBody UserRenewAccessTokenRequest request
    ) {
        var response = authService.renewAccessToken(request);
        return ResponseDto.created(response);
    }

    @Operation(summary = "사용자 로그아웃, refreshToken 삭제 처리")
    @PatchMapping("/logout")
    public ResponseEntity<Void> userLogout(AuthUser authUser) {
        authService.deleteRefreshToken(authUser);
        return ResponseDto.noContent();
    }

    @Operation(summary = "사용자 상태 조회")
    @GetMapping("/status")
    public ResponseEntity<?> userStatus(@Nullable AuthUser authUser) {
        if (AuthUserValidator.isAuthenticated(authUser)) {
            return ResponseDto.ok("Logged in User");
        }

        return ResponseDto.noContent();
    }
}
