package com.tdns.toks.api.domain.quiz.model.dto;

import com.tdns.toks.api.domain.quiz.model.QuizModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class QuizSolveDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuizSolveRequest {
        private String answer;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuizSolveResponse {
        private Long count;
        private String description;

        public static QuizSolveResponse of(Long quizReplyCount, QuizModel quiz) {
            return new QuizSolveDto.QuizSolveResponse(quizReplyCount, quiz.getDescription());
        }
    }
}
