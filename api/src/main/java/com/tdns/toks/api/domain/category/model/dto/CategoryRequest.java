package com.tdns.toks.api.domain.category.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CategoryRequest {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SetUserCategoriesRequest {
        private List<String> categories;
    }
}
