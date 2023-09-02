package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.cache.CacheFactory;
import com.tdns.toks.api.cache.CacheService;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.quizcomment.entity.QuizCommentLike;
import com.tdns.toks.core.domain.quizcomment.repository.QuizCommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizCommentLikeService {
    private final QuizCommentLikeRepository quizCommentLikeRepository;
    private final CacheService cacheService;

    @Transactional
    public void like(AuthUser authUser, Long commendId) {
        if (quizCommentLikeRepository.existsByCommentIdAndUid(commendId, authUser.getId())) {
            return;
        }

        quizCommentLikeRepository.save(
                QuizCommentLike.builder()
                        .uid(authUser.getId())
                        .commentId(commendId)
                        .build()
        );
    }

    @Transactional
    public void unlike(AuthUser authUser, Long commendId) {
        quizCommentLikeRepository.findByCommentIdAndUid(commendId, authUser.getId())
                .ifPresent(commentLike -> quizCommentLikeRepository.deleteById(commentLike.getId()));
    }

    public int count(long commentId) {
        var count = cacheService.getOrNull(CacheFactory.makeCachedQuizCommentCount(commentId));
        return count != null ? count : 0;
    }

    public boolean isLiked(long commentId, AuthUser authUser) {
        long userId = 0L;
        if (authUser != null) {
            userId = authUser.getId();
        }
        return quizCommentLikeRepository.existsByCommentIdAndUid(commentId, userId);
    }
}
