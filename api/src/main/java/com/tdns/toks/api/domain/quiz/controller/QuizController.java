package com.tdns.toks.api.domain.quiz.controller;

import com.tdns.toks.api.batch.job.QuizTagJob;
import com.tdns.toks.api.domain.quiz.model.dto.QuizSearchRequest;
import com.tdns.toks.api.domain.quiz.model.dto.QuizSolveDto;
import com.tdns.toks.api.domain.quiz.service.QuizService;
import com.tdns.toks.core.common.model.dto.PageableResponseDto;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "퀴즈", description = "QUIZ API")
@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;
    private final QuizTagJob quizTagJob;

    @GetMapping("/test")
    public void test() {
        quizTagJob.runEveryHourJob();
    }


    @Operation(summary = "퀴즈 상세 조회")
    @GetMapping("/quizzes/{quizId}")
    public ResponseEntity<?> get(
            @Nullable AuthUser authUser,
            @PathVariable Long quizId,
            HttpServletRequest httpServletRequest
    ) {
        var response = quizService.getDetail(authUser, quizId, httpServletRequest);
        return ResponseDto.ok(response);
    }

    @Operation(summary = "퀴즈 다건 조회")
    @GetMapping("/quizzes")
    public ResponseEntity<?> getAll(
            @Nullable AuthUser authUser,
            @ParameterObject QuizSearchRequest request
    ) {
        var response = quizService.search(authUser, request);
        return PageableResponseDto.ok(response);
    }

    @Operation(summary = "퀴즈 문제 풀이", description = "[임시] 추천 데이터는 최대 3개까지")
    @PostMapping("/quizzes/{quizId}")
    public ResponseEntity<?> solveQuiz(
            @Nullable AuthUser authUser,
            @PathVariable Long quizId,
            @RequestBody QuizSolveDto.QuizSolveRequest request,
            HttpServletRequest httpServletRequest
    ) {
        var response = quizService.solveQuiz(authUser, quizId, request, httpServletRequest);
        return ResponseDto.ok(response);
    }
}
