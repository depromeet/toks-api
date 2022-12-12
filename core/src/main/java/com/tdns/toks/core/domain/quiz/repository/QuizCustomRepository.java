package com.tdns.toks.core.domain.quiz.repository;

import java.util.List;
import java.util.Optional;

import com.tdns.toks.core.domain.quiz.model.dto.QuizSimpleDTO;

public interface QuizCustomRepository {
	Optional<QuizSimpleDTO> retrieveById(Long id);

	List<QuizSimpleDTO> retrieveByStudyId(Long studyId);
}
