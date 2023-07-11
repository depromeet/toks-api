package com.tdns.toks.api.domain.quiz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class QuizSoleDto {
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
    }
}
