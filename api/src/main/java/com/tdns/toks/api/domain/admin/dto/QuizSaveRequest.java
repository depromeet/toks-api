package com.tdns.toks.api.domain.admin.dto;

import com.tdns.toks.core.domain.quiz.type.QuizType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuizSaveRequest {
    private String title;
    private String categoryId;
    // type에 맞추어
    private String question;
    private QuizType quizType;
}
