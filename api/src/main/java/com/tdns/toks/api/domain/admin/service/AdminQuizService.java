package com.tdns.toks.api.domain.admin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tdns.toks.api.domain.admin.dto.QuizResponse;
import com.tdns.toks.api.domain.admin.dto.QuizSaveRequest;
import com.tdns.toks.api.domain.admin.dto.QuizSaveResponse;
import com.tdns.toks.core.common.utils.MapperUtil;
import com.tdns.toks.core.domain.auth.model.AuthUser;
import com.tdns.toks.core.domain.category.repository.CategoryRepository;
import com.tdns.toks.core.domain.quiz.model.dto.question.QuizQuestionAbImageModel;
import com.tdns.toks.core.domain.quiz.model.dto.question.QuizQuestionModel;
import com.tdns.toks.core.domain.quiz.model.dto.question.QuizQuestionOxImageModel;
import com.tdns.toks.core.domain.quiz.model.dto.question.QuizQuestionOxSimpleModel;
import com.tdns.toks.core.domain.quiz.model.entity.Quiz;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import com.tdns.toks.core.domain.quiz.type.QuizType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminQuizService {
    private final CategoryRepository categoryRepository;
    private final QuizRepository quizRepository;

    @Transactional
    public QuizSaveResponse insert(
            AuthUser authUser,
            QuizSaveRequest request
    ) {
        // 카테고리 체크
        if (!categoryRepository.existsById(request.getCategoryId())) {
            throw new RuntimeException("quiz Id 없음");
        }

        // 퀴즈 저장하기
        var question = MapperUtil.writeValueAsString(
                resolveQuizQuestionModel(request.getQuizType(), request.getQuestion())
        );

        var savedQuestion = quizRepository.save(
                Quiz.builder()
                        .title(request.getTitle())
                        .categoryId(request.getCategoryId())
                        .question(question)
                        .quizType(request.getQuizType())
                        .createdBy(1L)
                        // 이후 필드는 필요 없음 -- 컬럼 삭제도 필요함
                        .imageUrls(Collections.emptyList())
                        .startedAt(LocalDateTime.now())
                        .endedAt(LocalDateTime.now())
                        .build()
        );

        log.info("create quiz / adminUid : {}", authUser.getId());

        return new QuizSaveResponse(savedQuestion.getId());
    }

    private QuizQuestionModel resolveQuizQuestionModel(QuizType type, String question) {
        if (type == QuizType.A_B_IMAGE) {
            return MapperUtil.readValue(
                    question,
                    new TypeReference<QuizQuestionAbImageModel>() {
                    }
            );
        } else if (type == QuizType.O_X_IMAGE) {
            return MapperUtil.readValue(
                    question,
                    new TypeReference<QuizQuestionOxImageModel>() {
                    }
            );
        } else if (type == QuizType.O_X_SIMPLE) {
            return MapperUtil.readValue(
                    question,
                    new TypeReference<QuizQuestionOxSimpleModel>() {
                    }
            );
        } else {
            throw new RuntimeException("error");
        }
    }

    public QuizResponse get(AuthUser authUser, Long quizId) {
        var quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("error"));

        return new QuizResponse(quiz.getId());
    }

    @Transactional
    public QuizResponse update(AuthUser authUser, Long quizId, QuizSaveRequest request) {
        var quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("error"));

        // 카테고리 체크
        if (!categoryRepository.existsById(request.getCategoryId())) {
            throw new RuntimeException("quiz Id 없음");
        }

        // 퀴즈 저장하기
        var question = MapperUtil.writeValueAsString(
                resolveQuizQuestionModel(request.getQuizType(), request.getQuestion())
        );

        log.info("update quiz / adminUid : {}", authUser.getId());

        var updatedQuestion = quizRepository.save(
                quiz.update(
                        request.getTitle(),
                        request.getCategoryId(),
                        request.getQuizType(),
                        question
                )
        );

        return new QuizResponse(updatedQuestion.getId());
    }

    @Transactional
    public void delete(AuthUser authUser, Set<Long> ids) {
        quizRepository.deleteAllById(ids);
        log.info("delete quizzes");
    }
}
