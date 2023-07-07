package com.tdns.toks.api.domain.quiz.controller;

import com.tdns.toks.api.domain.quiz.service.QuizReplyHistoryApiService;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.tdns.toks.api.domain.quiz.model.dto.QuizReplyHistoryApiDTO.QuizReplyHistoriesResponse;
import static com.tdns.toks.api.domain.quiz.model.dto.QuizReplyHistoryApiDTO.QuizReplyHistoryRequest;
import static com.tdns.toks.api.domain.quiz.model.dto.QuizReplyHistoryApiDTO.QuizReplyHistoryResponse;

@Deprecated(since = "1", forRemoval = true)
@Tag(name = "QuizReplyHistoryController-V1", description = "QUIZ REPLY HISTORY API")
@RestController
// @RequestMapping(path = "/api/v1/quiz-reply-histories", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class LegacyQuizReplyHistoryController {
    private final QuizReplyHistoryApiService quizReplyHistoryApiService;

    @Deprecated(since = "1", forRemoval = true)
    // @PostMapping
    @Operation(summary = "퀴즈 답변 제출")
    public ResponseEntity<?> submit(@Validated @RequestBody final QuizReplyHistoryRequest request) {
        var response = quizReplyHistoryApiService.submit(request);
        return ResponseDto.ok(response);
    }

    @Deprecated(since = "1", forRemoval = true)
    // @GetMapping("/quizzes/{quizId}")
    @Operation(summary = "퀴즈 답변 다건 조회")
    public ResponseEntity<?> getAllByQuizId(
            @PathVariable final Long quizId,
            final Pageable pageable
    ) {
        var response = quizReplyHistoryApiService.getAllByQuizId(quizId, pageable);
        return ResponseDto.ok(response);
    }
}
