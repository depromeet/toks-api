package com.tdns.toks.api.domain.suggest.controller;

import com.tdns.toks.api.domain.suggest.model.dto.SuggestRegisterRequest;
import com.tdns.toks.api.domain.suggest.service.SuggestService;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Deprecated(since = "1", forRemoval = true)
@Tag(name = "똑스에게 제안하기", description = "SUGGEST API")
@RestController
// @RequestMapping(path = "/api/v1/suggests", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class LegacySuggestController {
    private final SuggestService suggestApiService;

    @Deprecated(since = "1", forRemoval = true)
    @Operation(summary = "똑스에게 제안하기")
   // @PostMapping
    public ResponseEntity<?> register(@RequestBody SuggestRegisterRequest request) {
        var response = suggestApiService.register(request);
        return ResponseDto.created(response);
    }
}
