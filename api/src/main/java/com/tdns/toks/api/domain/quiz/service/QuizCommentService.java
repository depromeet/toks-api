package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.domain.quiz.model.dto.comment.QuizCommentCreateRequest;
import com.tdns.toks.api.domain.quiz.model.dto.comment.QuizCommentResponse;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import com.tdns.toks.core.domain.quizcomment.model.entity.QuizComment;
import com.tdns.toks.core.domain.quizcomment.repository.QuizCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizCommentService {
    private final QuizCommentRepository quizCommentRepository;
    private final QuizRepository quizRepository;

    public Page<QuizCommentResponse> getAll(Long quizId, Integer page, Integer size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        return quizCommentRepository.findAllByQuizId(quizId, pageable)
                .map(QuizCommentResponse::from);
    }

    public QuizCommentResponse insert(AuthUser authUser, Long quizId, QuizCommentCreateRequest request) {
        if (!quizRepository.existsById(quizId)) {
            throw new SilentApplicationErrorException(ApplicationErrorType.NOT_FOUND_QUIZ_ERROR);
        }

        var quizComment = quizCommentRepository.save(
                new QuizComment(quizId, authUser.getId(), request.getComment())
        );

        return QuizCommentResponse.from(quizComment);
    }
}
