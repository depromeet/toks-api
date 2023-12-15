package com.tdns.toks.api.domain.admin.controller;

import com.tdns.toks.api.domain.admin.dto.AdminQuizResponse;
import com.tdns.toks.api.domain.admin.dto.AdminQuizSaveOrUpdateRequest;
import com.tdns.toks.api.domain.admin.service.AdminQuizService;
import com.tdns.toks.core.common.model.dto.PageableResponseDto;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import com.tdns.toks.core.domain.auth.annotation.AdminPermission;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Tag(name = "[어드민] 퀴즈관리", description = "QUIZ API")
@RestController
@RequestMapping(path = "/api/v1/admin/quizzes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AdminQuizController {
    private final AdminQuizService adminQuizService;

    @Operation(summary = "퀴즈 추가")
    @AdminPermission
    @PostMapping
    public ResponseEntity<ResponseDto<AdminQuizResponse>> insert(
            AuthUser authUser,
            @RequestBody AdminQuizSaveOrUpdateRequest request
    ) {
        var response = adminQuizService.insert(authUser, request);
        return ResponseDto.created(response);
    }

    @Operation(summary = "퀴즈 단건 조회")
    @AdminPermission
    @GetMapping(path = "/{quizId}")
    public ResponseEntity<ResponseDto<AdminQuizResponse>> get(AuthUser authUser, @PathVariable Long quizId) {
        var response = adminQuizService.get(authUser, quizId);
        return ResponseDto.ok(response);
    }

    @Operation(summary = "퀴즈 다건 조회")
    @AdminPermission
    @GetMapping
    public ResponseEntity<ResponseDto<PageableResponseDto<AdminQuizResponse>>> getAll(
            AuthUser authUser,
            @RequestParam int page,
            @RequestParam int size
    ) {
        var response = adminQuizService.getAll(authUser, page, size);
        return PageableResponseDto.ok(response);
    }

    @Operation(summary = "퀴즈 수정")
    @AdminPermission
    @PatchMapping(path = "/{quizId}")
    public ResponseEntity<ResponseDto<AdminQuizResponse>> update(
            AuthUser authUser,
            @PathVariable Long quizId,
            @RequestBody AdminQuizSaveOrUpdateRequest request
    ) {
        var response = adminQuizService.update(authUser, quizId, request);
        return ResponseDto.ok(response);
    }

    @Operation(summary = "퀴즈 삭제")
    @AdminPermission
    @DeleteMapping
    public ResponseEntity<Void> delete(AuthUser authUser, @RequestParam Set<Long> ids) {
        adminQuizService.delete(authUser, ids);
        return ResponseDto.noContent();
    }

    @Operation(summary = "퀴즈 캐싱 제어", description = "[DEV] 퀴즈 모델 캐싱 제어")
    @AdminPermission
    @PostMapping("/quizzes/evict-cache")
    public ResponseEntity<Void> evict(AuthUser authUser, @RequestParam(required = false) Set<Long> ids) {
        adminQuizService.evict(authUser, ids);
        return ResponseDto.noContent();
    }
}
