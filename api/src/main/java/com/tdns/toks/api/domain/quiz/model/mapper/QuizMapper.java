package com.tdns.toks.api.domain.quiz.model.mapper;

import com.tdns.toks.api.domain.category.model.dto.CategoryModel;
import com.tdns.toks.api.domain.quiz.model.dto.QuizDetailResponse;
import com.tdns.toks.api.domain.quiz.model.dto.QuizModel;
import com.tdns.toks.core.domain.quiz.entity.Quiz;

public class QuizMapper {
    public static QuizDetailResponse toQuizResponse(
            Quiz quiz,
            CategoryModel category,
            int quizReplyHistoryCount,
            int quizCommentCount
    ) {
        var quizModel = new QuizModel(
                quiz.getId(),
                quiz.getCategoryId(),
                quiz.getTitle(),
                quiz.getQuestion(),
                quiz.getQuizType(),
                quiz.getDescription(),
                quiz.getAnswer()
        );

        return new QuizDetailResponse(quizModel, category, quizReplyHistoryCount, quizCommentCount);
    }

    public static QuizDetailResponse toQuizResponse(
            QuizModel quiz,
            CategoryModel category,
            int quizReplyHistoryCount,
            int quizCommentCount
    ) {
        return new QuizDetailResponse(quiz, category, quizReplyHistoryCount, quizCommentCount);
    }
}
