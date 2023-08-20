package com.tdns.toks.api.domain.quiz.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tdns.toks.api.domain.category.model.CategoryModel;
import com.tdns.toks.api.domain.quiz.model.QuizInfoModel;
import com.tdns.toks.api.domain.quiz.model.QuizModel;
import com.tdns.toks.api.domain.quiz.model.QuizReplyCountsModel;
import com.tdns.toks.api.domain.quiz.model.QuizReplyModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizDetailResponse {
    private QuizModel quiz;
    private CategoryModel category;
    private int quizCommentCount;
    @JsonProperty("isSubmitted")
    private boolean isSubmitted;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private QuizReplyModel quizReply;
    private QuizReplyCountsModel quizReplyCount;

    public static QuizDetailResponse of(
            QuizInfoModel quizInfo,
            QuizReplyModel replyModel,
            QuizReplyCountsModel replyCountModel
    ) {
        return new QuizDetailResponse(
                quizInfo.getQuiz(),
                quizInfo.getCategory(),
                quizInfo.getQuizCommentCount(),
                replyModel != null,
                replyModel,
                replyCountModel
        );
    }
}
