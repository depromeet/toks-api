package com.tdns.toks.api.domain.auth.controller;

import com.tdns.toks.api.domain.auth.service.AuthV2Service;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증인가 관리", description = "Auth")
@RestController
@RequestMapping(path = "/api/v2/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthV2Controller {
    private final AuthV2Service authV2Service;

    @Operation(summary = "인증 사용자 본인 정보 조회")
    @GetMapping("/my-infos")
    public ResponseEntity<?> getMyInfos(
            @Parameter(hidden = true) AuthUser authUser
    ) {
        var response = authV2Service.getMyInfos(authUser);
        return ResponseEntity.ok(response);
    }
}
