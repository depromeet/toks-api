package com.tdns.toks.api.domain.quiz.model;

import com.tdns.toks.core.domain.quiz.model.QuizReplyCountModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class QuizReplyCountsModel {
    private Long totalCount;
    private Map<String, ReplyModel> replyCount;

    @Data
    @AllArgsConstructor
    public static class ReplyModel {
        private String answer;
        private Long count;

        public static ReplyModel from(QuizReplyCountModel model) {
            return new ReplyModel(model.getAnswer(), model.getCount());
        }
    }
}
