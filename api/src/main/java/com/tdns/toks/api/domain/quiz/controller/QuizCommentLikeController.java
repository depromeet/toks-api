package com.tdns.toks.api.domain.quiz.controller;

import com.tdns.toks.api.domain.quiz.service.QuizCommentLikeService;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "퀴즈 좋아요", description = "QUIZ Comment Like API")
@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class QuizCommentLikeController {
    private final QuizCommentLikeService quizCommentLikeService;

    @Operation(summary = "댓글 좋아요")
    @Parameter(name = "authUser", hidden = true)
    @PostMapping(path = "/comments/{commendId}/like")
    public ResponseEntity<Void> like(AuthUser authUser, @PathVariable Long commendId) {
        quizCommentLikeService.like(authUser, commendId);
        return ResponseDto.noContent();
    }

    @Operation(summary = "댓글 좋아요 취소")
    @Parameter(name = "authUser", hidden = true)
    @PostMapping(path = "/comments/{commendId}/unlike")
    public ResponseEntity<Void> unlike(AuthUser authUser, @PathVariable Long commendId) {
        quizCommentLikeService.unlike(authUser, commendId);
        return ResponseDto.noContent();
    }
}
