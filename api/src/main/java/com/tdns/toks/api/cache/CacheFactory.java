package com.tdns.toks.api.cache;

import com.tdns.toks.api.domain.quiz.model.QuizModel;

public class CacheFactory {
    public static Cache<QuizModel> makeCachedQuiz(Long id) {
        return new Cache<>(
                "quiz:model:" + id,
                QuizModel.class
        );
    }

    public static Cache<Integer> makeCachedQuizReplyHistoryCount(Long id) {
        return new Cache<>(
                "quiz:reply-history:count:" + id,
                Integer.class
        );
    }

    public static Cache<Integer> makeCachedQuizCommentCount(Long id) {
        return new Cache<>(
                "quiz:comment:count:" + id,
                Integer.class
        );
    }
}
