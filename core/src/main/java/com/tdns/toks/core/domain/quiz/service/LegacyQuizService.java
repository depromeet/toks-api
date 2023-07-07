package com.tdns.toks.core.domain.quiz.service;

import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.quiz.model.dto.QuizSimpleDTO;
import com.tdns.toks.core.domain.quiz.model.entity.Quiz;
import com.tdns.toks.core.domain.quiz.repository.QuizReplyHistoryRepository;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import com.tdns.toks.core.domain.quiz.type.QuizStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class LegacyQuizService {
    private final QuizRepository quizRepository;
    private final QuizReplyHistoryRepository quizReplyHistoryRepository;

    public Quiz getOrThrow(final Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_QUIZ_ERROR));
    }

    public QuizSimpleDTO retrieveByIdOrThrow(final Long id) {
        return quizRepository.retrieveById(id)
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_QUIZ_ERROR));
    }

    public QuizStatusType getQuizStatus(final LocalDateTime startedAt, final LocalDateTime endedAt) {
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(startedAt)) {
            return QuizStatusType.TO_DO;
        } else if (now.isAfter(endedAt)) {
            return QuizStatusType.DONE;
        }
        return QuizStatusType.IN_PROGRESS;
    }

    public Quiz save(final Quiz quiz) {
        return quizRepository.save(quiz);
    }
}
