package com.tdns.toks.api.domain.fab.controller;

import com.tdns.toks.api.domain.fab.service.FabService;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "FAB 페이지 관련 데이터 제공", description = "FAB data")
@RestController
@RequestMapping(path = "/api/v1/fab", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class FabController {
    private final FabService fabService;

    @GetMapping
    public ResponseEntity<?> getFab(
            @Nullable AuthUser authUser,
            @RequestParam int year,
            @RequestParam int month
    ) {
        var response = fabService.getFabInfo(authUser, month, year);
        return ResponseDto.ok(response);
    }
}
