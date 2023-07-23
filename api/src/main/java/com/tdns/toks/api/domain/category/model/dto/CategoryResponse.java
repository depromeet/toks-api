package com.tdns.toks.api.domain.category.model.dto;

import com.tdns.toks.api.domain.category.model.CategoryModel;
import com.tdns.toks.core.domain.category.entity.UserCategory;
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
    public static class GetUserCategoriesResponse {
        private List<String> categoryIds;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SetUserCategoriesResponse {
        private long userId;
        private List<String> categoryIds;

        public SetUserCategoriesResponse(UserCategory userCategory) {
            this.userId = userCategory.getUserId();
            this.categoryIds = userCategory.getCategoryIds();
        }
    }
}
