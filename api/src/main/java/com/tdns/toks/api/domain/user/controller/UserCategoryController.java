package com.tdns.toks.api.domain.user.controller;

import com.tdns.toks.api.domain.category.model.dto.CategoryRequest;
import com.tdns.toks.api.domain.user.service.UserCategoryService;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저 카테고리 정보", description = "User Category API")
@RestController
@RequestMapping(path = "/api/v1/categories", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserCategoryController {
    private final UserCategoryService userCategoryService;

    @Operation(summary = "사용자 카테고리 조회")
    @GetMapping
    public ResponseEntity<?> getUserCategory(AuthUser authUser) {
        var response = userCategoryService.getUserCategories(authUser);
        return ResponseDto.ok(response);
    }

    @Operation(summary = "사용자 카테고리 설정")
    @PostMapping
    public ResponseEntity<?> setUserCategory(AuthUser authUser, @RequestBody CategoryRequest.SetUserCategoriesRequest request) {
        var response = userCategoryService.setUserCategories(authUser, request.getCategories());
        return ResponseDto.created(response);
    }
}
