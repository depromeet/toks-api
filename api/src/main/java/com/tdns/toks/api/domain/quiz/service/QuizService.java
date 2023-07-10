package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.domain.category.service.CategoryService;
import com.tdns.toks.api.domain.quiz.model.dto.QuizDetailResponse;
import com.tdns.toks.api.domain.quiz.model.dto.QuizRecModel;
import com.tdns.toks.api.domain.quiz.model.dto.QuizSimpleResponse;
import com.tdns.toks.api.domain.quiz.model.mapper.QuizMapper;
import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final CategoryService categoryService;
    private final QuizCommentService quizCommentService;
    private final QuizReplyHistoryService quizReplyHistoryService;
    private final QuizCacheService quizCacheService;

    @SneakyThrows
    public QuizDetailResponse get(AuthUser authUser, Long quizId) {
        var quiz = quizCacheService.getCachedQuiz(quizId);
        var category = categoryService.get(quiz.getCategoryId())
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_CATEGORY_ERROR));

        var quizReplyHistoryCount = quizReplyHistoryService.count(quizId);
        var quizCommentCount = quizCommentService.count(quizId);

        return QuizMapper.toQuizResponse(quiz, category, quizReplyHistoryCount, quizCommentCount);
    }

    // TODO : 성능 개선 필요.. 꼭
    public Page<QuizDetailResponse> getAll(
            AuthUser authUser,
            Set<String> categoryIds,
            Integer page,
            Integer size
    ) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        return quizRepository.findAllByCategoryIdIn(categoryIds, pageable)
                .map(quiz -> {
                            var category = categoryService.get(quiz.getCategoryId())
                                    .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_CATEGORY_ERROR));

                            var quizReplyHistoryCount = quizReplyHistoryService.count(quiz.getId());
                            var quizCommentCount = quizCommentService.count(quiz.getId());

                            return QuizMapper.toQuizResponse(quiz, category, quizReplyHistoryCount, quizCommentCount);
                        }
                );
    }

    // TODO : 개선 필요
    // TODO : 퀴즈 추천 데이터를 별도로 가져야 할듯....
    public QuizRecModel getRecModels(AuthUser authUser, String categoryId) {
        var recQuizzes = quizRepository.findTop3ByCategoryId(categoryId)
                .stream()
                .map(quiz -> {
                            var category = categoryService.get(quiz.getCategoryId())
                                    .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_CATEGORY_ERROR));

                            var quizReplyHistoryCount = quizReplyHistoryService.count(quiz.getId());
                            var quizCommentCount = quizCommentService.count(quiz.getId());

                            return QuizMapper.toQuizResponse(quiz, category, quizReplyHistoryCount, quizCommentCount);
                        }
                ).collect(Collectors.toList());

        return new QuizRecModel(recQuizzes);
    }
}
