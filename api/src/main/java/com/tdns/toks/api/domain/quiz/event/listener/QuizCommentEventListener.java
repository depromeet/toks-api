package com.tdns.toks.api.domain.quiz.event.listener;

import com.tdns.toks.api.cache.CacheFactory;
import com.tdns.toks.api.cache.CacheService;
import com.tdns.toks.api.domain.quiz.event.model.QuizCommentInsertEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class QuizCommentEventListener {
    private final CacheService cacheService;

    @TransactionalEventListener
    public void handle(QuizCommentInsertEvent event) {
        cacheService.increment(CacheFactory.makeCachedQuizCommentCount(event.getQuizId()));
    }
}
