package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.domain.quiz.model.mapper.QuizReplyHistoryMapper;
import com.tdns.toks.core.domain.quiz.service.QuizReplyHistoryService;
import com.tdns.toks.core.domain.quiz.service.LegacyQuizService;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.tdns.toks.api.domain.quiz.model.dto.QuizReplyHistoryApiDTO.QuizReplyHistoriesResponse;
import static com.tdns.toks.api.domain.quiz.model.dto.QuizReplyHistoryApiDTO.QuizReplyHistoryRequest;
import static com.tdns.toks.api.domain.quiz.model.dto.QuizReplyHistoryApiDTO.QuizReplyHistoryResponse;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class QuizReplyHistoryApiService {
    private final QuizReplyHistoryService quizReplyHistoryService;
    private final LegacyQuizService quizService;
    private final QuizReplyHistoryMapper mapper;

    public QuizReplyHistoryResponse submit(final QuizReplyHistoryRequest request) {
        var user = UserDetailDTO.get();
        quizService.getOrThrow(request.getQuizId());
        quizReplyHistoryService.checkAlreadySubmitted(request.getQuizId(), UserDetailDTO.get().getId());

        var quizReply = mapper.toEntity(request);
        var savedQuizReply = quizReplyHistoryService.save(quizReply);

        log.info("submit Quiz Reply / uid : {} / quizId : {} /quizReplyHistoryId : {}", user.getId(), request.getQuizId(), savedQuizReply.getId());

        return QuizReplyHistoryResponse.toResponse(savedQuizReply);
    }

    public QuizReplyHistoriesResponse getAllByQuizId(final Long quizId, final Pageable pageable) {
        var quiz = quizService.getOrThrow(quizId);
        var quizHistory = quizReplyHistoryService.getAllByQuizId(quiz.getId(), pageable);
        return new QuizReplyHistoriesResponse(quizHistory);
    }
}
