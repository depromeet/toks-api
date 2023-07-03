package com.tdns.toks.api.domain.quiz.model.mapper;

import com.tdns.toks.api.domain.quiz.model.dto.QuizDetailResponse;
import com.tdns.toks.core.domain.quiz.model.entity.Quiz;

public class QuizMapper {
    public static QuizDetailResponse toQuizResponse(Quiz quiz) {
        return new QuizDetailResponse(
                quiz.getId(),
                quiz.getTitle(),
                quiz.getCategoryId(),
                quiz.getQuestion(),
                quiz.getQuizType(),
                quiz.getDescription(),
                quiz.getAnswer(),
                quiz.getCreatedBy()
        );
    }
}
