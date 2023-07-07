package com.tdns.toks.api.domain.quiz.model.dto;

import com.tdns.toks.api.domain.category.model.dto.CategoryModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizDetailResponse {
    private QuizModel quiz;
    private CategoryModel category;
}
