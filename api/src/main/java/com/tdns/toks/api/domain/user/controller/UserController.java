package com.tdns.toks.api.domain.user.controller;

import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserUpdateNicknameResponse;
import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserRenewAccessTokenResponse;
import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserRenewAccessTokenRequest;
import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserInfoResponse;
import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserUpdateNicknameRequest;
import com.tdns.toks.api.domain.user.service.UserApiService;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "UserController-V1", description = "USER API")
@RestController
@RequestMapping(path = "/api/v1/user", produces = "application/json")
@RequiredArgsConstructor
public class UserController {
    private final UserApiService userApiService;

    @GetMapping
    @Operation(
            method = "GET",
            summary = "사용자 정보 조회"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Access Token", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
    public ResponseEntity<UserInfoResponse> getUserInformation(
    ) {
        var response = userApiService.getUserInfo();
        return ResponseDto.ok(response);
    }

    @PatchMapping("/nickname")
    @Operation(
            method = "PATCH",
            summary = "사용자 닉네임 설정"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserUpdateNicknameResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Access Token", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
    public ResponseEntity<UserUpdateNicknameResponse> updateNickname(
            @RequestBody UserUpdateNicknameRequest userUpdateNicknameRequest
    ) {
        var response = userApiService.updateNickname(userUpdateNicknameRequest);
        return ResponseDto.created(response);
    }

    @PostMapping("/renew")
    @Operation(
            method = "POST",
            summary = "사용자 accessToken 갱신 요청"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserRenewAccessTokenResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Access Token", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
    public ResponseEntity<UserRenewAccessTokenResponse> renewAccessToken(
            @RequestBody UserRenewAccessTokenRequest userRenewAccessTokenRequest
    ) {
        var response = userApiService.renewAccessToken(userRenewAccessTokenRequest);
        return ResponseDto.created(response);
    }

    @PatchMapping("/logout")
    @Operation(
            method = "PATCH",
            summary = "사용자 로그아웃, refreshToken 삭제 처리"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid Access Token", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)})
    public ResponseEntity<Void> userLogout() {
        userApiService.deleteRefreshToken();
        return ResponseDto.noContent();
    }
}
