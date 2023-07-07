package com.tdns.toks.api.domain.quiz.model.dto;

import com.tdns.toks.core.domain.quiz.type.QuizType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
public class QuizModel {
    private final Long id;
    private final String title;
    private final Map<String, Object> question;
    private final QuizType quizType;
    private final String description;
    private final String answer;
}
