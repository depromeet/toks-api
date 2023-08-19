package com.tdns.toks.api.domain.fab.controller;

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

    @Operation(summary = "FAB 데이터 제공", description = "FAB 페이지에서 필요한 모든 데이터 일괄 제공")
    @GetMapping
    public ResponseEntity<?> getFab(
            AuthUser authUser,
            @RequestParam int year,
            @RequestParam int month
    ) {
        var response = fabService.getFabInfo(authUser, month, year);
        return ResponseDto.ok(response);
    }
}
