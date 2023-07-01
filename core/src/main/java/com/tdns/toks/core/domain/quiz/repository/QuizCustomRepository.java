package com.tdns.toks.core.domain.quiz.repository;

import com.tdns.toks.core.domain.quiz.model.dto.QuizSimpleDTO;

import java.util.Optional;

public interface QuizCustomRepository {
    Optional<QuizSimpleDTO> retrieveById(Long id);
}
