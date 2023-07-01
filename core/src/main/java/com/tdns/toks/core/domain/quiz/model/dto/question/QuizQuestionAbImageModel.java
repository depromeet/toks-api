package com.tdns.toks.core.domain.quiz.model.dto.question;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// A_B_IMAGE
@Data
@NoArgsConstructor
public class QuizQuestionAbImageModel implements QuizQuestionModel {
    private String question;
    private List<QuestionAbImageModel> models;

    @Data
    @NoArgsConstructor
    public static class QuestionAbImageModel {
        private String imageUrl;
        private Button button;
    }
}
