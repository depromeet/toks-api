package com.tdns.toks.api.domain.quiz.model;

import com.tdns.toks.api.domain.category.model.CategoryModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizInfoModel {
    private QuizModel quiz;
    private CategoryModel category;
    private Integer quizCommentCount;
}
