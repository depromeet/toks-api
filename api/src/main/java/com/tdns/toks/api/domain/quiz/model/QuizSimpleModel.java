package com.tdns.toks.api.domain.quiz.model;

import com.tdns.toks.api.domain.category.model.CategoryModel;
import com.tdns.toks.api.domain.quiz.model.QuizModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizSimpleModel {
    private QuizModel quiz;
    private CategoryModel category;
    private int quizReplyHistoryCount;
    private int quizCommentCount;
}
