package com.tdns.toks.api.domain.quiz.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tdns.toks.api.domain.category.model.CategoryModel;
import com.tdns.toks.api.domain.quiz.model.QuizInfoModel;
import com.tdns.toks.api.domain.quiz.model.QuizModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizDetailResponse {
    private QuizModel quiz;
    private CategoryModel category;
    private int quizReplyHistoryCount;
    private int answerReplyCount;
    private int quizCommentCount;
    @JsonProperty("isSubmitted")
    private boolean isSubmitted;

    public static QuizDetailResponse of(QuizInfoModel quizInfo, boolean isSubmitted) {
        return new QuizDetailResponse(
                quizInfo.getQuiz(),
                quizInfo.getCategory(),
                quizInfo.getQuizReplyHistoryCount(),
                quizInfo.getAnswerReplyCount().intValue(),
                quizInfo.getQuizCommentCount(),
                isSubmitted
        );
    }
}
