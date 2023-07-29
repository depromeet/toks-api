package com.tdns.toks.api.domain.quiz.controller;

import com.tdns.toks.api.domain.quiz.model.dto.QuizSolveDto;
import com.tdns.toks.api.domain.quiz.service.QuizService;
import com.tdns.toks.core.common.model.dto.PageableResponseDto;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Tag(name = "퀴즈", description = "QUIZ API")
@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @Operation(summary = "퀴즈 단건 조회")
    @GetMapping("/quizzes/{quizId}")
    public ResponseEntity<?> get(
            @Nullable AuthUser authUser,
            @PathVariable Long quizId,
            HttpServletRequest httpServletRequest
    ) {
        var response = quizService.get(authUser, quizId, httpServletRequest);
        return ResponseDto.ok(response);
    }

    @Operation(summary = "퀴즈 다건 조회")
    @GetMapping("/quizzes")
    public ResponseEntity<?> getAll(
            @Nullable AuthUser authUser,
            @RequestParam Set<String> categoryIds,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        var response = quizService.getAll(
                authUser,
                categoryIds,
                page,
                size
        );

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
