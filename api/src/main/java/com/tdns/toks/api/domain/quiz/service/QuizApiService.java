package com.tdns.toks.api.domain.quiz.service;

import static com.tdns.toks.api.domain.quiz.model.dto.QuizApiDTO.*;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.core.domain.quiz.model.dto.QuizSimpleDTO;
import com.tdns.toks.core.domain.quiz.service.QuizService;
import com.tdns.toks.core.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizApiService {
	private final QuizService quizService;
	private final UserService userService;

	public QuizSimpleResponse getById(final Long id) {
		return QuizSimpleResponse.toResponse(quizService.retrieveByIdOrThrow(id));
	}

	public QuizzesResponse getAllByStudyId(final Long studyId) {
		var unSubmitters = userService.filterUnSubmitterByStudyId(studyId);
		var quizzes = quizService.retrieveByStudyId(studyId);
		var results = new ArrayList<QuizResponse>();

		for (QuizSimpleDTO quizz : quizzes) {
			var unSubmitter = unSubmitters.stream()
				.filter(userSimpleByQuizIdDTO -> userSimpleByQuizIdDTO.getQuizId().equals(quizz.getQuizId()))
				.findFirst()
				.get()
				.getUsers();
			results.add(QuizResponse.toResponse(quizz, unSubmitter));
		}

		return new QuizzesResponse(results);
	}

}
