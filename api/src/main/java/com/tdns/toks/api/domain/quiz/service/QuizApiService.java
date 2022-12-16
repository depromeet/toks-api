package com.tdns.toks.api.domain.quiz.service;

import static com.tdns.toks.api.domain.quiz.model.dto.QuizApiDTO.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tdns.toks.api.domain.quiz.model.mapper.QuizMapper;
import com.tdns.toks.core.common.service.S3UploadService;
import com.tdns.toks.core.domain.quiz.model.dto.QuizSimpleDTO;
import com.tdns.toks.core.domain.quiz.service.QuizService;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import com.tdns.toks.core.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizApiService {
	private final QuizService quizService;
	private final UserService userService;
	private final S3UploadService s3UploadService;

	private final QuizMapper mapper;

	public QuizSimpleResponse getById(final Long id) {
		return QuizSimpleResponse.toResponse(quizService.retrieveByIdOrThrow(id));
	}

	public QuizzesResponse getAllByStudyId(final Long studyId) {
		var unSubmitters = userService.filterUnSubmitterByStudyId(studyId);
		var quizzes = quizService.retrieveByStudyId(studyId);
		var results = new ArrayList<QuizResponse>();

		for (QuizSimpleDTO quiz : quizzes) {
			var unSubmitter = unSubmitters.stream()
				.filter(userSimpleByQuizIdDTO -> userSimpleByQuizIdDTO.getQuizId().equals(quiz.getQuizId()))
				.findFirst()
				.get()
				.getUsers();
			results.add(QuizResponse.toResponse(
				quiz,
				unSubmitter,
				quizService.getQuizStatus(quiz.getStartedAt(), quiz.getEndedAt()))
			);
		}

		return new QuizzesResponse(results);
	}

	public QuizCreateResponse create(
		final QuizRequest quizRequest,
		final List<MultipartFile> multipartFiles
	) {
		List<String> urls = s3UploadService.uploadFiles(multipartFiles, UserDetailDTO.get().getId().toString());
		return QuizCreateResponse.toResponse(quizService.save(mapper.toEntity(quizRequest, urls)));
	}
}
