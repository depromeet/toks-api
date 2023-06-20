package com.tdns.toks.api.domain.admin.controller;

import com.tdns.toks.api.domain.admin.dto.QuizSaveRequest;
import com.tdns.toks.api.domain.admin.service.AdminQuizService;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin-QuizController-V2", description = "QUIZ API")
@RestController
@RequestMapping(path = "/api/v2/admin/quizzes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AdminQuizController {
    private final AdminQuizService adminQuizService;

    // 퀴즈 생성
    @Operation(summary = "퀴즈 추가")
    @Parameter(name = "authUser", hidden = true)
    @PostMapping
    public ResponseEntity<?> insert(
            AuthUser authUser,
            @RequestBody QuizSaveRequest request
    ) {
        var response = adminQuizService.insert(authUser, request);
        return ResponseDto.created(response);
    }

    // 퀴즈 단건 조회
    @Operation(summary = "퀴즈 단건 조회")
    @Parameter(name = "authUser", hidden = true)
    @GetMapping(path = "/{quizId}")
    public ResponseEntity<?> get(
            AuthUser authUser,
            @PathVariable Long quizId
    ) {
        var response = adminQuizService.get(authUser, quizId);
        return ResponseDto.ok(response);
    }

    // 퀴즈 수정

    // 퀴즈 삭제
}
