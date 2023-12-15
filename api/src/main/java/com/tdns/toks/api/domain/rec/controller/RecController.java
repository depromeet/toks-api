package com.tdns.toks.api.domain.rec.controller;

import com.tdns.toks.api.domain.rec.model.dto.QuizRecResponse;
import com.tdns.toks.api.domain.rec.service.RecQuizService;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "추천 서비스", description = "Rec API")
@RestController
@RequestMapping(path = "/api/v1/rec", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RecController {
    private final RecQuizService recQuizService;

    @Operation(summary = "퀴즈 추천 데이터 조회", description = "추천 데이터는 최대 3개까지")
    @GetMapping("/quizzes")
    public ResponseEntity<ResponseDto<QuizRecResponse>> getRecQuizzes(
            @Nullable AuthUser authUser,
            @RequestParam(name = "quizId") Long quizId
    ) {
        var response = recQuizService.getRecQuizModels(authUser, quizId);
        return ResponseDto.ok(response);
    }
}
