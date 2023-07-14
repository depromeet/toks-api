package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.cache.CacheFactory;
import com.tdns.toks.api.cache.CacheService;
import com.tdns.toks.api.domain.quiz.model.dto.QuizCommentCreateRequest;
import com.tdns.toks.api.domain.quiz.model.dto.QuizCommentResponse;
import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.quizcomment.model.entity.QuizComment;
import com.tdns.toks.core.domain.quizcomment.repository.QuizCommentRepository;
import com.tdns.toks.core.domain.user.model.entity.User;
import com.tdns.toks.core.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizCommentService {
    private final QuizCommentRepository quizCommentRepository;
    private final QuizCacheService quizCacheService;
    private final UserRepository userRepository;
    private final CacheService cacheService;
    private final QuizCommentLikeService quizCommentLikeService;

    public Page<QuizCommentResponse> getAll(Long quizId, Integer page, Integer size) {
        var quiz = quizCacheService.getCachedQuiz(quizId);

        var pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        var quizComment = quizCommentRepository.findAllByQuizId(quizId, pageable);

        var uids = quizComment.getContent().stream().map(QuizComment::getUid).collect(Collectors.toSet());
        var user = userRepository.findAllById(uids)
                .stream().collect(Collectors.toMap(User::getId, User::getNickname));

        return quizComment.map(comment -> {
                    var likeCount = quizCommentLikeService.count(comment.getId());
                    return QuizCommentResponse.from(comment, user.get(comment.getUid()), likeCount);
                }
        );
    }

    @Transactional
    public QuizCommentResponse insert(AuthUser authUser, Long quizId, QuizCommentCreateRequest request) {
        var quiz = quizCacheService.getCachedQuiz(quizId);

        var quizComment = quizCommentRepository.save(
                new QuizComment(quizId, authUser.getId(), request.getComment())
        );

        var user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_USER));

        var likeCount = quizCommentLikeService.count(quizComment.getId());

        return QuizCommentResponse.from(quizComment, user.getNickname(), likeCount);
    }

    public int count(long quizId) {
        var count = cacheService.getOrNull(CacheFactory.makeCachedQuizCommentCount(quizId));
        return count != null ? count : 0;
    }
}
