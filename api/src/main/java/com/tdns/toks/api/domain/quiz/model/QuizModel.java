package com.tdns.toks.api.domain.quiz.model;

import com.tdns.toks.core.domain.quiz.entity.Quiz;
import com.tdns.toks.core.domain.quiz.type.QuizType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizModel {
    private Long id;
    private String categoryId;
    private String title;
    private List<String> tags;
    private Map<String, Object> question;
    private QuizType quizType;
    private String description;
    private String answer;

    public static QuizModel from(Quiz quiz) {
        return new QuizModel(
                quiz.getId(),
                quiz.getCategoryId(),
                quiz.getTitle(),
                quiz.getTags(),
                quiz.getQuestion(),
                quiz.getQuizType(),
                quiz.getDescription(),
                quiz.getAnswer()
        );
    }
}
