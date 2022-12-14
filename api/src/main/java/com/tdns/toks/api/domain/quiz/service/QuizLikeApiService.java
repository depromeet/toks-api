package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.domain.quiz.model.dto.QuizLikeApiDTO;
import com.tdns.toks.api.domain.quiz.model.dto.QuizLikeApiDTO.QuizLikeRequest;
import com.tdns.toks.api.domain.quiz.model.mapper.QuizLikeMapper;
import com.tdns.toks.core.domain.quiz.service.QuizLikeService;
import com.tdns.toks.core.domain.quiz.service.QuizReplyHistoryService;
import com.tdns.toks.core.domain.quiz.service.QuizService;
import com.tdns.toks.core.domain.quizrank.service.QuizRankService;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class QuizLikeApiService {
    private final QuizLikeService quizLikeService;
    private final QuizReplyHistoryService quizReplyHistoryService;
    private final QuizRankService quizRankService;
    private final QuizService quizService;
    private final QuizLikeMapper mapper;

    public QuizLikeApiDTO.QuizLikeResponse like(final QuizLikeRequest request) {
        var user = UserDetailDTO.get();

        var quizReplyHistory = quizReplyHistoryService.getOrThrow(request.getQuizReplyHistoryId());
        quizLikeService.checkAlreadyLike(UserDetailDTO.get().getId(), quizReplyHistory.getQuizId());

        var quiz = quizService.getOrThrow(quizReplyHistory.getQuizId());
        quizRankService.updateScore(quizReplyHistory.getCreatedBy(), quiz.getStudyId());

        var quizLike = mapper.toEntity(request);
        var savedQuizLike = quizLikeService.create(quizLike);

        log.info("quiz like / uid : {} / quizId : {} / quizLikeID : {}", user.getId(), quiz.getId(), savedQuizLike.getId());

        return QuizLikeApiDTO.QuizLikeResponse.toResponse(savedQuizLike);
    }
}
