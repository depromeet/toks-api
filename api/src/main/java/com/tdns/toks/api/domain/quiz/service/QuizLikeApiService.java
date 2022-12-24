package com.tdns.toks.api.domain.quiz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.api.domain.quiz.model.dto.QuizLikeApiDTO;
import com.tdns.toks.api.domain.quiz.model.dto.QuizLikeApiDTO.QuizLikeRequest;
import com.tdns.toks.api.domain.quiz.model.mapper.QuizLikeMapper;
import com.tdns.toks.core.domain.quiz.service.QuizLikeService;
import com.tdns.toks.core.domain.quiz.service.QuizReplyHistoryService;
import com.tdns.toks.core.domain.quiz.service.QuizService;
import com.tdns.toks.core.domain.quizrank.service.QuizRankService;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;

import lombok.RequiredArgsConstructor;

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
		var quizReplyHistory = quizReplyHistoryService.getOrThrow(request.getQuizReplyHistoryId());
		quizLikeService.checkAlreadyLike(UserDetailDTO.get().getId(), quizReplyHistory.getQuizId());

		var quiz = quizService.getOrThrow(quizReplyHistory.getQuizId());
		quizRankService.updateScore(quizReplyHistory.getCreatedBy(), quiz.getStudyId());

		return QuizLikeApiDTO.QuizLikeResponse.toResponse(quizLikeService.create(mapper.toEntity(request)));
	}
}
