package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.core.domain.quiz.repository.QuizReplyHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class QuizReplyHistoryService {
    private final QuizReplyHistoryRepository quizReplyHistoryRepository;

    public Long countByQuizIdAndAnswer(Long quizId, String answer) {
        return quizReplyHistoryRepository.countByQuizIdAndAnswer(quizId, answer);
    }

    @Async
    public CompletableFuture<Long> asyncCountByQuizIdAndAnswer(Long quizId, String answer) {
        return CompletableFuture.completedFuture(countByQuizIdAndAnswer(quizId, answer));
    }
}
