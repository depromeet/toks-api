package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.cache.CacheFactory;
import com.tdns.toks.api.cache.CacheService;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.quizcomment.model.entity.QuizCommentLike;
import com.tdns.toks.core.domain.quizcomment.repository.QuizCommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizCommentLikeService {
    private final QuizCommentLikeRepository quizCommentLikeRepository;
    private final CacheService cacheService;

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

    public int count(long commentId) {
        var count = cacheService.getOrNull(CacheFactory.makeCachedQuizCommentCount(commentId));

        if (count == null) {
            return 0;
        }

        return count;
    }
}
