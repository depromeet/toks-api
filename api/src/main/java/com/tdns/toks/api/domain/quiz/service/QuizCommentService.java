package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.domain.quiz.model.dto.comment.QuizCommentCreateRequest;
import com.tdns.toks.api.domain.quiz.model.dto.comment.QuizCommentResponse;
import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import com.tdns.toks.core.domain.quizcomment.model.entity.QuizComment;
import com.tdns.toks.core.domain.quizcomment.repository.QuizCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class QuizCommentService {
    private final QuizCommentRepository quizCommentRepository;
    private final QuizRepository quizRepository;
    // TODO : 분리
    private final StringRedisTemplate redisTemplate;

    public final static String QUIZ_REPLY_HISTORY_CACHE = "quiz:comment:count:";

    public Page<QuizCommentResponse> getAll(Long quizId, Integer page, Integer size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        return quizCommentRepository.findAllByQuizId(quizId, pageable)
                .map(QuizCommentResponse::from);
    }

    public QuizCommentResponse insert(AuthUser authUser, Long quizId, QuizCommentCreateRequest request) {
        if (!quizRepository.existsById(quizId)) {
            throw new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_QUIZ_ERROR);
        }

        var quizComment = quizCommentRepository.save(
                new QuizComment(quizId, authUser.getId(), request.getComment())
        );

        return QuizCommentResponse.from(quizComment);
    }

    public int count(long quizId) {
        var count = redisTemplate.opsForValue().get(
                QUIZ_REPLY_HISTORY_CACHE + quizId
        );

        if (count == null) {
            return 0;
        }

        return Integer.parseInt(count);
    }

    @Async
    public CompletableFuture<Integer> asyncCount(long quizId) {
        return CompletableFuture.completedFuture(count(quizId));
    }
}
