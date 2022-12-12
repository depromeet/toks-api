package com.tdns.toks.api.domain.quiz.service;

import static com.tdns.toks.api.domain.quiz.model.dto.QuizReplyHistoryApiDTO.*;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.api.domain.quiz.model.mapper.QuizReplyHistoryMapper;
import com.tdns.toks.core.domain.quiz.service.QuizService;
import com.tdns.toks.core.domain.quiz.service.QuizReplyHistoryService;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizReplyHistoryApiService {
	private final QuizReplyHistoryService quizReplyHistoryService;
	private final QuizService quizService;
	private final QuizReplyHistoryMapper mapper;

	public QuizReplyHistoryResponse submit(final QuizReplyHistoryRequest request) {
		quizService.getOrThrow(request.getQuizId());
		quizReplyHistoryService.checkAlreadySubmitted(request.getQuizId(), UserDetailDTO.get().getId());
		return QuizReplyHistoryResponse.toResponse(quizReplyHistoryService.save(mapper.toEntity(request)));
	}

	public QuizReplyHistoriesResponse getAllByQuizId(final Long quizId, final Pageable pageable) {
		var quiz = quizService.getOrThrow(quizId);
		return new QuizReplyHistoriesResponse(quizReplyHistoryService.getAllByQuizId(quiz.getId(), pageable));
	}
}
