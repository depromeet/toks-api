package com.tdns.toks.core.domain.quizrank.repository;

import java.util.List;

import com.tdns.toks.core.domain.quizrank.model.dto.QuizRankDTO;

public interface CustomQuizRankRepository {

	List<QuizRankDTO> retrieveByStudyId(final Long studyId);
}
