package com.tdns.toks.api.domain.admin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminQuizResponse {
    private Long id;
    private String title;
    private List<String> tags;
    private String categoryId;
    private Map<String, Object> question;
    private QuizType quizType;
    private String description;
    private String answer;
    private Long createdBy;

    public static AdminQuizResponse from(Quiz quiz) {
        return new AdminQuizResponse(
                quiz.getId(),
                quiz.getTitle(),
                quiz.getTags(),
                quiz.getCategoryId(),
                quiz.getQuestion(),
                quiz.getQuizType(),
                quiz.getDescription(),
                quiz.getAnswer(),
                quiz.getCreatedBy()
        );
    }
}
