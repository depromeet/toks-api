package com.tdns.toks.api.domain.admin.service;

import com.tdns.toks.api.domain.admin.dto.AdminQuizResponse;
import com.tdns.toks.api.domain.admin.dto.AdminQuizSaveOrUpdateRequest;
import com.tdns.toks.core.common.exception.ApplicationErrorException;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.category.repository.CategoryRepository;
import com.tdns.toks.core.domain.quiz.entity.Quiz;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminQuizService {
    private final QuizRepository quizRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public AdminQuizResponse insert(AuthUser authUser, AdminQuizSaveOrUpdateRequest request) {
        if (!categoryRepository.existsById(request.getCategoryId())) {
            throw new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_CATEGORY_ERROR);
        }

        var question = quizRepository.save(
                Quiz.builder()
                        .title(request.getTitle())
                        .categoryId(request.getCategoryId())
                        .question(request.getQuestion())
                        .quizType(request.getQuizType())
                        .createdBy(authUser.getId())
                        .build()
        );

        log.info("create quiz / adminUid : {}", authUser.getId());

        return AdminQuizResponse.from(question);
    }

    public AdminQuizResponse get(AuthUser authUser, Long quizId) {
        var quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_QUIZ_ERROR));

        return AdminQuizResponse.from(quiz);
    }

    @Transactional
    public AdminQuizResponse update(AuthUser authUser, Long quizId, AdminQuizSaveOrUpdateRequest request) {
        var quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_QUIZ_ERROR));

        if (!categoryRepository.existsById(request.getCategoryId())) {
            throw new ApplicationErrorException(ApplicationErrorType.NOT_FOUND_CATEGORY_ERROR);
        }

        var updatedQuestion = quizRepository.save(
                quiz.update(
                        request.getTitle(),
                        request.getCategoryId(),
                        request.getQuizType(),
                        request.getQuestion()
                )
        );

        log.info("update quiz / adminUid : {}", authUser.getId());

        return AdminQuizResponse.from(updatedQuestion);
    }

    @Transactional
    public void delete(AuthUser authUser, Set<Long> ids) {
        quizRepository.deleteAllById(ids);
        log.info("delete quizzes");
    }

    public Page<AdminQuizResponse> getAll(AuthUser authUser, int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        return quizRepository.findAll(pageable).map(AdminQuizResponse::from);
    }
}
