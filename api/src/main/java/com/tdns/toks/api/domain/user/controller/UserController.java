package com.tdns.toks.api.domain.user.controller;

import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserInfoResponse;
import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserRenewAccessTokenRequest;
import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserRenewAccessTokenResponse;
import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserUpdateNicknameRequest;
import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserUpdateNicknameResponse;
import com.tdns.toks.api.domain.user.service.UserApiService;
import com.tdns.toks.core.common.model.dto.ResponseDto;
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

@Deprecated(since = "1", forRemoval = true)
@Tag(name = "사용자 관리 서비스", description = "USER API")
@RestController
@RequestMapping(path = "/api/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {
    private final UserApiService userApiService;

    @Deprecated(since = "1", forRemoval = true)
    @GetMapping
    @Operation(summary = "사용자 정보 조회")
    public ResponseEntity<UserInfoResponse> getUserInformation(
    ) {
        var response = userApiService.getUserInfo();
        return ResponseDto.ok(response);
    }

    @Deprecated(since = "1", forRemoval = true)
    @PatchMapping("/nickname")
    @Operation(summary = "사용자 닉네임 설정")
    public ResponseEntity<UserUpdateNicknameResponse> updateNickname(
            @RequestBody UserUpdateNicknameRequest userUpdateNicknameRequest
    ) {
        var response = userApiService.updateNickname(userUpdateNicknameRequest);
        return ResponseDto.created(response);
    }

    @Deprecated(since = "1", forRemoval = true)
    @PostMapping("/renew")
    @Operation(summary = "사용자 accessToken 갱신 요청", description = "/api/v1/auth/renew 로 변경")
    public ResponseEntity<UserRenewAccessTokenResponse> renewAccessToken(
            @RequestBody UserRenewAccessTokenRequest userRenewAccessTokenRequest
    ) {
        var response = userApiService.renewAccessToken(userRenewAccessTokenRequest);
        return ResponseDto.created(response);
    }

    @Deprecated(since = "1", forRemoval = true)
    @PatchMapping("/logout")
    @Operation(summary = "사용자 로그아웃, refreshToken 삭제 처리", description = "/api/v1/auth/logout 로 변경")
    public ResponseEntity<Void> userLogout() {
        userApiService.deleteRefreshToken();
        return ResponseDto.noContent();
    }
}
