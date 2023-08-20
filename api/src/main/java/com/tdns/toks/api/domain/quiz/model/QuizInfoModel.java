package com.tdns.toks.api.domain.quiz.model;

import com.tdns.toks.api.domain.category.model.CategoryModel;
import com.tdns.toks.core.domain.quiz.entity.Quiz;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizInfoModel {
    private QuizModel quiz;
    private CategoryModel category;
    private Integer quizCommentCount;

    public static QuizInfoModel of(QuizModel quiz, CategoryModel category, Integer quizCommentCount) {
        return new QuizInfoModel(quiz, category, quizCommentCount);
    }

    public static QuizInfoModel of(Quiz quiz, CategoryModel category, int quizCommentCount) {
        return new QuizInfoModel(QuizModel.from(quiz), category, quizCommentCount);
    }
}
