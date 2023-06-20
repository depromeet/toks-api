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
import com.tdns.toks.core.domain.user.type.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;

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
        if (!authUser.getUserRole().equals(UserRole.ADMIN)) {
            throw new RuntimeException("error");
        }

        // 카테고리 체크
        if (!categoryRepository.existsById(request.getCategoryId())) {
            throw new RuntimeException("quiz Id 없음");
        }

        // 퀴즈 저장하기
        var question = MapperUtil.writeValueAsString(resolveQuizQuestionModel(request));

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
                        .studyId(1L)
                        .round(0)
                        .build()
        );

        log.info("create quiz / adminUid : {}", authUser.getId());

        return new QuizSaveResponse(savedQuestion.getId());
    }

    private QuizQuestionModel resolveQuizQuestionModel(QuizSaveRequest request) {
        if (request.getQuizType() == QuizType.A_B_IMAGE) {
            return MapperUtil.readValue(
                    request.getQuestion(),
                    new TypeReference<QuizQuestionAbImageModel>() {
                    }
            );
        } else if (request.getQuizType() == QuizType.O_X_IMAGE) {
            return MapperUtil.readValue(
                    request.getQuestion(),
                    new TypeReference<QuizQuestionOxImageModel>() {
                    }
            );
        } else if (request.getQuizType() == QuizType.O_X_SIMPLE) {
            return MapperUtil.readValue(
                    request.getQuestion(),
                    new TypeReference<QuizQuestionOxSimpleModel>() {
                    }
            );
        } else {
            throw new RuntimeException("error");
        }
    }

    public QuizResponse get(AuthUser authUser, Long quizId) {
        if (!authUser.getUserRole().equals(UserRole.ADMIN)) {
            throw new RuntimeException("error");
        }

        var quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("error"));

        return new QuizResponse(quiz.getId());
    }
}
