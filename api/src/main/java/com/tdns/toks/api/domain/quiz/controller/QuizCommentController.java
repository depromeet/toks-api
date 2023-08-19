package com.tdns.toks.api.domain.quiz.controller;

import com.tdns.toks.api.domain.quiz.model.dto.QuizCommentCreateRequest;
import com.tdns.toks.api.domain.quiz.service.QuizCommentService;
import com.tdns.toks.core.common.model.dto.PageableResponseDto;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "퀴즈 댓글 관리", description = "QUIZ Comment API")
@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class QuizCommentController {
    private final QuizCommentService quizCommentService;

    @Operation(summary = "댓글 다건 조회", description = "내림차순으로 제공")
    @GetMapping("/quizzes/{quizId}/comments")
    public ResponseEntity<?> getAll(
            @PathVariable Long quizId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size
    ) {
        var response = quizCommentService.getAll(quizId, page, size);
        return PageableResponseDto.ok(response);
    }

    @Operation(summary = "댓글 작성")
    @PostMapping("/quizzes/{quizId}/comments")
    public ResponseEntity<?> insert(
            AuthUser authUser,
            @PathVariable Long quizId,
            @ModelAttribute @RequestBody QuizCommentCreateRequest request
    ) {
        var response = quizCommentService.insert(authUser, quizId, request);
        return ResponseDto.created(response);
    }
}
