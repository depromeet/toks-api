package com.tdns.toks.api.domain.category.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class CategoryResponse {
    private final List<CategoryModel> categories;
}
