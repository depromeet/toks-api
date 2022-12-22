package com.tdns.toks.api.domain.quiz.service;

import static com.tdns.toks.api.domain.quiz.model.dto.QuizApiDTO.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.api.domain.quiz.model.mapper.QuizMapper;
import com.tdns.toks.core.common.service.S3UploadService;
import com.tdns.toks.core.domain.quiz.model.dto.QuizSimpleDTO;
import com.tdns.toks.core.domain.quiz.service.QuizService;
import com.tdns.toks.core.domain.study.service.StudyUserService;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import com.tdns.toks.core.domain.user.model.dto.UserSimpleByQuizIdDTO;

import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizApiService {
	private final QuizService quizService;
	private final StudyUserService studyUserService;
	private final S3UploadService s3UploadService;

	private final QuizMapper mapper;

	public QuizSimpleResponse getById(final Long id) {
		return QuizSimpleResponse.toResponse(quizService.retrieveByIdOrThrow(id));
	}

	public QuizzesResponse getAllByStudyId(final Long studyId) {
		var unSubmitters = studyUserService.filterUnSubmitterByStudyId(studyId);
		var quizzes = quizService.retrieveByStudyId(studyId);
		var results = new ArrayList<QuizResponse>();

		for (QuizSimpleDTO quiz : quizzes) {
			var unSubmitter = unSubmitters.stream()
				.filter(userSimpleByQuizIdDTO -> userSimpleByQuizIdDTO.getQuizId().equals(quiz.getQuizId()))
				.findFirst()
				.map(UserSimpleByQuizIdDTO::getUsers)
				.orElseGet(Collections::emptyList);
			results.add(QuizResponse.toResponse(
				quiz,
				unSubmitter,
				quizService.getQuizStatus(quiz.getStartedAt(), quiz.getEndedAt()))
			);
		}

		return new QuizzesResponse(results);
	}

	public QuizCreateResponse create(
		final QuizRequest quizRequest
	) {
		var urls = s3UploadService.uploadFiles(Optional.ofNullable(quizRequest.getImageFiles()).orElse(Collections.emptyList()), UserDetailDTO.get().getId().toString());
		val quizzes = quizService.save(mapper.toEntity(quizRequest, urls));
		return QuizCreateResponse.toResponse(quizzes);
	}
}
