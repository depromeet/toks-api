package com.tdns.toks.api.domain.user.model.dto;

import com.tdns.toks.core.domain.user.entity.UserCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class UserCategoryResponse {
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
