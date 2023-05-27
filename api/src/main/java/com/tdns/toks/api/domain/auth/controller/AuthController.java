package com.tdns.toks.api.domain.auth.controller;

import com.tdns.toks.api.domain.auth.service.AuthService;
import com.tdns.toks.api.domain.user.model.dto.UserApiDTO;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증인가 관리", description = "Auth")
@RestController
@RequestMapping(path = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/renew")
    @Operation(summary = "사용자 accessToken 갱신 요청")
    public ResponseEntity<UserApiDTO.UserRenewAccessTokenResponse> renewAccessToken(
            @RequestBody UserApiDTO.UserRenewAccessTokenRequest request
    ) {
        var response = authService.renewAccessToken(request);
        return ResponseDto.created(response);
    }

    @PatchMapping("/logout")
    @Operation(summary = "사용자 로그아웃, refreshToken 삭제 처리")
    public ResponseEntity<Void> userLogout() {
        authService.deleteRefreshToken();
        return ResponseDto.noContent();
    }
}
