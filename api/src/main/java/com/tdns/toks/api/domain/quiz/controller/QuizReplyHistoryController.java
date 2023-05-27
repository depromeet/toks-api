package com.tdns.toks.api.domain.quiz.controller;

import com.tdns.toks.api.domain.quiz.service.QuizReplyHistoryApiService;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tdns.toks.api.domain.quiz.model.dto.QuizReplyHistoryApiDTO.QuizReplyHistoriesResponse;
import static com.tdns.toks.api.domain.quiz.model.dto.QuizReplyHistoryApiDTO.QuizReplyHistoryRequest;
import static com.tdns.toks.api.domain.quiz.model.dto.QuizReplyHistoryApiDTO.QuizReplyHistoryResponse;

@Tag(name = "QuizReplyHistoryController-V1", description = "QUIZ REPLY HISTORY API")
@RestController
@RequestMapping(path = "/api/v1/quiz-reply-histories", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class QuizReplyHistoryController {
    private final QuizReplyHistoryApiService quizReplyHistoryApiService;

    @PostMapping
    @Operation(summary = "퀴즈 답변 제출")
    public ResponseEntity<QuizReplyHistoryResponse> submit(@Validated @RequestBody final QuizReplyHistoryRequest request) {
        var response = quizReplyHistoryApiService.submit(request);
        return ResponseDto.ok(response);
    }

    @GetMapping("/quizzes/{quizId}")
    @Operation(summary = "퀴즈 답변 다건 조회")
    public ResponseEntity<QuizReplyHistoriesResponse> getAllByQuizId(
            @PathVariable final Long quizId,
            final Pageable pageable
    ) {
        var response = quizReplyHistoryApiService.getAllByQuizId(quizId, pageable);
        return ResponseDto.ok(response);
    }
}
