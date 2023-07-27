package com.tdns.toks.api.domain.quiz.model.mapper;

import com.tdns.toks.api.domain.category.model.CategoryModel;
import com.tdns.toks.api.domain.quiz.model.QuizInfoModel;
import com.tdns.toks.api.domain.quiz.model.QuizModel;
import com.tdns.toks.core.domain.quiz.entity.Quiz;

public class QuizMapper {
    public static QuizInfoModel toQuizInfoResponse(
            Quiz quiz,
            CategoryModel category,
            int quizReplyHistoryCount,
            int quizCommentCount
    ) {
        var quizModel = new QuizModel(
                quiz.getId(),
                quiz.getCategoryId(),
                quiz.getTitle(),
                quiz.getTags(),
                quiz.getQuestion(),
                quiz.getQuizType(),
                quiz.getDescription(),
                quiz.getAnswer()
        );

        return new QuizInfoModel(quizModel, category, quizReplyHistoryCount, quizCommentCount);
    }
}
