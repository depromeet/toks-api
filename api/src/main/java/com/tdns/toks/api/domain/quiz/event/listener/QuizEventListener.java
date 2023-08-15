package com.tdns.toks.api.domain.quiz.event.listener;

import com.tdns.toks.api.domain.quiz.event.model.QuizSolveEvent;
import com.tdns.toks.api.domain.quiz.service.QuizCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class QuizEventListener {
    private final QuizCacheService quizCacheService;

    @TransactionalEventListener
    public void handle(QuizSolveEvent event) {
        quizCacheService.incrementQuizReply(event.getQuizId());
    }
}
