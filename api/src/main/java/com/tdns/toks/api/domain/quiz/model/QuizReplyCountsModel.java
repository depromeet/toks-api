package com.tdns.toks.api.domain.quiz.model;

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
    }
}