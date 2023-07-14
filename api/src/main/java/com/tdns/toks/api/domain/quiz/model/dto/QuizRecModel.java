package com.tdns.toks.api.domain.quiz.model.dto;

import com.tdns.toks.api.domain.quiz.model.dto.QuizDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuizRecModel {
    private List<QuizDetailResponse> quizRecommendModels;
}
