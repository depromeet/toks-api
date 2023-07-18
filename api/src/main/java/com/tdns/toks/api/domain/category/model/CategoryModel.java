package com.tdns.toks.api.domain.category.model;

import com.tdns.toks.core.domain.category.entity.Category;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CategoryModel {
    private final String id;
    private final Integer depth;
    private final String name;
    private final String description;

    public static CategoryModel from(Category category) {
        return new CategoryModel(
                category.getId(),
                category.getDepth(),
                category.getName(),
                category.getDescription()
        );
    }
}
