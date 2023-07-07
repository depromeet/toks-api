package com.tdns.toks.core.domain.quiz.service;

import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.quiz.model.entity.QuizLike;
import com.tdns.toks.core.domain.quiz.repository.QuizLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizLikeService {
    private final QuizLikeRepository quizLikeRepository;

    public QuizLike create(final QuizLike quizLike) {
        return quizLikeRepository.save(quizLike);
    }

    public void checkAlreadyLike(final Long userId, final Long quizId) {
        if (quizLikeRepository.countByUserIdAndQuizId(userId, quizId) == 1) {
            throw new ApplicationErrorException(ApplicationErrorType.ALREADY_LIKE_USER_QUIZ);
        }
    }

    public boolean isVoted(final Long userId, final Long quizId) {
        return quizLikeRepository.countByUserIdAndQuizId(userId, quizId) == 1;
    }
}
