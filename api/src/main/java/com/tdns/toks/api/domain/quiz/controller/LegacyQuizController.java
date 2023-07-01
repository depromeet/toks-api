package com.tdns.toks.api.domain.quiz.controller;

import com.tdns.toks.api.domain.quiz.service.LegacyQuizApiService;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.tdns.toks.api.domain.quiz.model.dto.QuizApiDTO.QuizSimpleResponse;

@Deprecated(since = "1", forRemoval = true)
@Tag(name = "QuizController-V1", description = "QUIZ API")
@RestController
// @RequestMapping(path = "/api/v1/quizzes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class LegacyQuizController {
    private final LegacyQuizApiService quizApiService;

    @Deprecated(since = "1", forRemoval = true)
    //  @GetMapping("/{quizId}")
    @Operation(summary = "퀴즈 단건 조회", description = "상세 설명을 기록")
    public ResponseEntity<QuizSimpleResponse> getById(@PathVariable final Long quizId) {
        var response = quizApiService.getById(quizId);
        return ResponseDto.ok(response);
    }
}
