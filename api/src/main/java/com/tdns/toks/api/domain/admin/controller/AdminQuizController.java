package com.tdns.toks.api.domain.admin.controller;

import com.tdns.toks.api.domain.admin.dto.AdminQuizSaveOrUpdateRequest;
import com.tdns.toks.api.domain.admin.service.AdminQuizService;
import com.tdns.toks.core.common.model.dto.PageableResponseDto;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import com.tdns.toks.core.domain.auth.annotation.AdminPermission;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @Parameter(name = "authUser", hidden = true)
    @AdminPermission
    @PostMapping
    public ResponseEntity<?> insert(
            AuthUser authUser,
            @RequestBody AdminQuizSaveOrUpdateRequest request
    ) {
        var response = adminQuizService.insert(authUser, request);
        return ResponseDto.created(response);
    }

    @Operation(summary = "퀴즈 단건 조회")
    @Parameter(name = "authUser", hidden = true)
    @AdminPermission
    @GetMapping(path = "/{quizId}")
    public ResponseEntity<?> get(
            AuthUser authUser,
            @PathVariable Long quizId
    ) {
        var response = adminQuizService.get(authUser, quizId);
        return ResponseDto.ok(response);
    }

    @Operation(summary = "퀴즈 다건 조회")
    @Parameter(name = "authUser", hidden = true)
    @AdminPermission
    @GetMapping
    public ResponseEntity<?> getAll(
            AuthUser authUser,
            @RequestParam int page,
            @RequestParam int size
    ) {
        var response = adminQuizService.getAll(authUser, page, size);
        return PageableResponseDto.ok(response);
    }

    @Operation(summary = "퀴즈 수정")
    @Parameter(name = "authUser", hidden = true)
    @AdminPermission
    @PatchMapping(path = "/{quizId}")
    public ResponseEntity<?> update(
            AuthUser authUser,
            @PathVariable Long quizId,
            @RequestBody AdminQuizSaveOrUpdateRequest request
    ) {
        var response = adminQuizService.update(authUser, quizId, request);
        return ResponseDto.ok(response);
    }

    @Operation(summary = "퀴즈 삭제")
    @Parameter(name = "authUser", hidden = true)
    @AdminPermission
    @DeleteMapping
    public ResponseEntity<Void> delete(
            AuthUser authUser,
            @RequestParam Set<Long> ids
    ) {
        adminQuizService.delete(authUser, ids);
        return ResponseDto.noContent();
    }
}
