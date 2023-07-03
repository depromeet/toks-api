package com.tdns.toks.api.domain.quiz.model.dto;

import com.tdns.toks.core.domain.quiz.type.QuizType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizDetailResponse {
    private Long id;
    private String title;
    private String categoryId;
    // TODO : 객체로 보낼지 타협 필요
    private String question;
    private QuizType quizType;
    private String description;
    private String answer;
    private Long createdBy;
}
