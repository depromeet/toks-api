package com.tdns.toks.api.domain.quiz.event.listener;

import com.tdns.toks.api.domain.quiz.event.model.QuizSolveEvent;
import com.tdns.toks.api.domain.quiz.service.QuizCacheService;
import com.tdns.toks.api.domain.user.service.UserActivityCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class QuizEventListener {
    private final QuizCacheService quizCacheService;
    private final UserActivityCountService userActivityCountService;

    @TransactionalEventListener
    public void handle(QuizSolveEvent event) {
        quizCacheService.incrementQuizReply(event.getQuizId());

        if (event.getUid() != -1) {
            userActivityCountService.getUserActivityCountOrGenerate(event.getUid()).updateTotalSolveCount();
        }
    }
}
