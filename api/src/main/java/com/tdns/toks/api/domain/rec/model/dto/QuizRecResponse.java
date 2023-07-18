package com.tdns.toks.api.domain.rec.model.dto;

import com.tdns.toks.api.domain.quiz.model.QuizSimpleModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuizRecResponse {
    private List<QuizSimpleModel> quizRecommendModels;
}
