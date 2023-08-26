package com.tdns.toks.api.domain.quiz.model;

import com.tdns.toks.api.domain.category.model.CategoryModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizInfoModel {
    private QuizModel quiz;
    private CategoryModel category;
    private Integer quizReplyHistoryCount;
    private Long answerReplyCount;
    private Integer quizCommentCount;

    public static QuizInfoModel of(
            QuizModel quiz,
            CategoryModel category,
            Integer quizReplyHistoryCount,
            Long answerReplyCountCf,
            int quizCommentCount
    ) {
        return new QuizInfoModel(
                quiz,
                category,
                quizReplyHistoryCount,
                answerReplyCountCf,
                quizCommentCount
        );
    }
}
