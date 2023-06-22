package com.tdns.toks.api.domain.quiz.controller;

import com.tdns.toks.api.domain.quiz.model.dto.QuizLikeApiDTO.QuizLikeRequest;
import com.tdns.toks.api.domain.quiz.model.dto.QuizLikeApiDTO.QuizLikeResponse;
import com.tdns.toks.api.domain.quiz.service.QuizLikeApiService;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Deprecated(since = "1", forRemoval = true)
@Tag(name = "QuizLikeController-V1", description = "QUIZ LIKE API")
@RestController
// @RequestMapping(path = "/api/v1/quiz-likes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class LegacyQuizLikeController {
    private final QuizLikeApiService quizLikeApiService;

    @Deprecated(since = "1", forRemoval = true)
    // @PostMapping
    @Operation(summary = "퀴즈 답변 좋아요")
    public ResponseEntity<QuizLikeResponse> like(@Validated @RequestBody final QuizLikeRequest request) {
        var response = quizLikeApiService.like(request);
        return ResponseDto.ok(response);
    }
}