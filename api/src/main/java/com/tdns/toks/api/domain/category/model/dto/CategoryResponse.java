package com.tdns.toks.api.domain.category.model.dto;

import com.tdns.toks.api.domain.category.model.CategoryModel;
import com.tdns.toks.core.domain.user.entity.UserCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CategoryResponse {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetAllCategoriesResponse {
        List<CategoryModel> categories;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetMainCategories {
        private List<GetMainCategory> categories;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetMainCategory {
        private String id;
        private Integer depth;
        private String name;
        private String description;
        private List<CategoryModel> subCategories;

        public static CategoryResponse.GetMainCategory of(
                CategoryModel mainCategory,
                List<CategoryModel> subCategories
        ) {
            return new CategoryResponse.GetMainCategory(
                    mainCategory.getId(),
                    mainCategory.getDepth(),
                    mainCategory.getName(),
                    mainCategory.getDescription(),
                    subCategories
            );
        }
    }
}
