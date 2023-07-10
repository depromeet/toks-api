package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.quizcomment.model.entity.QuizCommentLike;
import com.tdns.toks.core.domain.quizcomment.repository.QuizCommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class QuizCommentLikeService {
    private final QuizCommentLikeRepository quizCommentLikeRepository;
    // TODO : 분리
    private final StringRedisTemplate redisTemplate;

    public final static String QUIZ_COMMENT_LIKE_CACHE = "quiz:comment:like:";

    public void like(AuthUser authUser, Long commendId) {
        if (!quizCommentLikeRepository.existsByCommentIdAndUid(commendId, authUser.getId())) {
            quizCommentLikeRepository.save(
                    QuizCommentLike.builder()
                            .uid(authUser.getId())
                            .commentId(commendId)
                            .build()
            );
        }
    }

    public void unlike(AuthUser authUser, Long commendId) {
        quizCommentLikeRepository.findByCommentIdAndUid(commendId, authUser.getId())
                .ifPresent(commentLike -> quizCommentLikeRepository.deleteById(commentLike.getId()));
    }

    public int count(long quizId) {
        var count = redisTemplate.opsForValue().get(
                QUIZ_COMMENT_LIKE_CACHE + quizId
        );

        if (count == null) {
            return 0;
        }

        return Integer.parseInt(count);
    }
}
