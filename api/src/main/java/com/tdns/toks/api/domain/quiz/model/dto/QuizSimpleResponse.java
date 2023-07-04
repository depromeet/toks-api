package com.tdns.toks.api.domain.quiz.model.dto;

import com.tdns.toks.core.domain.quiz.model.entity.Quiz;
import com.tdns.toks.core.domain.quiz.type.QuizType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizSimpleResponse {
    private Long id;
    private String title;
    private String categoryId;
    private String question;
    private QuizType quizType;
    private String description;

    public static QuizSimpleResponse from(Quiz quiz) {
        return new QuizSimpleResponse(
                quiz.getId(),
                quiz.getTitle(),
                quiz.getCategoryId(),
                quiz.getQuestion(),
                quiz.getQuizType(),
                quiz.getDescription()
        );
    }
}
