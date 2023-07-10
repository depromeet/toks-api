package com.tdns.toks.api.domain.quiz.model.dto;

import com.tdns.toks.core.domain.quiz.type.QuizType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizModel {
    private Long id;
    private String categoryId;
    private String title;
    private Map<String, Object> question;
    private QuizType quizType;
    private String description;
    private String answer;
}