package com.tdns.toks.api.domain.fab.controller;

import com.tdns.toks.api.domain.fab.model.dto.FabDto;
import com.tdns.toks.api.domain.fab.service.FabService;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "FAB 서비스", description = "FAB API")
@RestController
@RequestMapping(path = "/api/v1/fab", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class FabController {
    private final FabService fabService;

    @Operation(summary = "FAB 유저 퀴즈 데이터", description = "사용자 총 접속 및 문제 풀이 횟수 제공")
    @GetMapping("/user")
    public ResponseEntity<ResponseDto<FabDto.GetFabUserDataResponseDto>> getFabUserData(
            AuthUser authUser,
            @RequestParam int year,
            @RequestParam int month
    ) {
        var response = fabService.getFabUserData(authUser, month, year);
        return ResponseDto.ok(response);
    }

    @Operation(summary = "FAB 월별 풀이 데이터", description = "사용자 해당 월 일별 문제 풀이 횟수 제공")
    @GetMapping("/calendar")
    public ResponseEntity<ResponseDto<FabDto.GetFabCalendarDataResponseDto>> getFabCalendarData(
            AuthUser authUser,
            @RequestParam int year,
            @RequestParam int month
    ) {
        var response = fabService.getFabCalendarData(authUser, month, year);
        return ResponseDto.ok(response);
    }
}
