package com.tdns.toks.api.domain.category.controller;

import com.tdns.toks.api.domain.category.model.dto.CategoryRequest.SetUserCategoriesRequest;
import com.tdns.toks.api.domain.category.service.CategoryService;
import com.tdns.toks.core.common.model.dto.ResponseDto;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "카테고리 정보", description = "Category API")
@RestController
@RequestMapping(path = "/api/v1/categories", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "전체 카테고리 정보 조회")
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        var response = categoryService.getAll();
        return ResponseDto.ok(response);
    }

    @Operation(summary = "사용자 카테고리 조회")
    @Parameter(name = "authUser", hidden = true)
    @GetMapping
    public ResponseEntity<?> getUserCategory(
            AuthUser authUser
    ) {
        var response = categoryService.getUserCategories(authUser);
        return ResponseDto.ok(response);
    }

    @Operation(summary = "사용자 카테고리 설정")
    @Parameter(name = "authUser", hidden = true)
    @PostMapping
    public ResponseEntity<?> setUserCategory(
            AuthUser authUser,
            @RequestBody SetUserCategoriesRequest request
    ) {
        var response = categoryService.setUserCategories(authUser, request.getCategories());
        return ResponseDto.created(response);
    }
}
