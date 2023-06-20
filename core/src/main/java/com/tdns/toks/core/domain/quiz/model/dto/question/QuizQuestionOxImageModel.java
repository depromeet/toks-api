package com.tdns.toks.core.domain.quiz.model.dto.question;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// O_X_IMAGE
@Data
@NoArgsConstructor
public class QuizQuestionOxImageModel implements QuizQuestionModel {
    private String question;
    private String questionImageUrl;
    private List<QuestionOxImageModel> models;

    @Data
    @NoArgsConstructor
    public static class QuestionOxImageModel {
        private String image;
        private Button button;
    }
}
