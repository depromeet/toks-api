package com.tdns.toks.api.domain.quiz.model.mapper;

import com.tdns.toks.api.domain.category.model.CategoryModel;
import com.tdns.toks.api.domain.quiz.model.QuizInfoModel;
import com.tdns.toks.api.domain.quiz.model.QuizModel;
import com.tdns.toks.core.domain.quiz.entity.Quiz;

public class QuizMapper {
    public static QuizInfoModel toQuizInfoResponse(
            Quiz quiz,
            CategoryModel category,
            int quizCommentCount
    ) {
        return new QuizInfoModel(QuizModel.from(quiz), category, quizCommentCount);
    }
}
