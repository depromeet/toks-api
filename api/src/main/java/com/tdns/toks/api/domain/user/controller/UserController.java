package com.tdns.toks.api.domain.user.controller;

import com.tdns.toks.api.domain.user.model.dto.UserApiDTO.UserUpdateNicknameRequest;
import com.tdns.toks.api.domain.user.service.UserService;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저", description = "User")
@RestController
@RequestMapping(path = "/api/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "사용자 닉네임 설정")
    @Parameter(name = "authUser", hidden = true)
    @PatchMapping("/nickname")
    public ResponseEntity<?> setUserNickname(
            AuthUser authUser,
            @RequestBody UserUpdateNicknameRequest nicknameRequest) {
        var response = userService.updateNickname(authUser.getId(), nicknameRequest.getNickname());
        return ResponseDto.ok(response);
    }
}
