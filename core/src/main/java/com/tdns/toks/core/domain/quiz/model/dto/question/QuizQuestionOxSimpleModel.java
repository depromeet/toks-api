package com.tdns.toks.core.domain.quiz.model.dto.question;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// O_X_SIMPLE
@Data
@NoArgsConstructor
public class QuizQuestionOxSimpleModel implements QuizQuestionModel {
    private String question;
    private List<QuestionOxSimpleModel> models;

    @Data
    @NoArgsConstructor
    public static class QuestionOxSimpleModel {
        private String imageUrl;
        private Button button;
    }
}
