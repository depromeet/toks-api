package com.tdns.toks.core.domain.quiz.service;

import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.quiz.model.dto.QuizReplyHistoryDto;
import com.tdns.toks.core.domain.quiz.model.entity.QuizReplyHistory;
import com.tdns.toks.core.domain.quiz.repository.QuizReplyHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizReplyHistoryService {
    private final QuizReplyHistoryRepository quizReplyHistoryRepository;

    public QuizReplyHistory save(final QuizReplyHistory quizReplyHistory) {
        return quizReplyHistoryRepository.save(quizReplyHistory);
    }

    public QuizReplyHistory getOrThrow(final Long id) {
        return quizReplyHistoryRepository.findById(id)
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.INVALID_REQUEST));
    }

    public void checkAlreadySubmitted(final Long quizId, final Long userId) {
        if (quizReplyHistoryRepository.existsByQuizIdAndCreatedBy(quizId, userId)) {
            throw new ApplicationErrorException(ApplicationErrorType.ALREADY_SUBMITTED_USER_QUIZ);
        }
    }

    public List<QuizReplyHistoryDto> getAllByQuizId(final Long quizId, final Pageable pageable) {
        return quizReplyHistoryRepository.retrieveByQuizId(quizId, pageable);
    }
}
