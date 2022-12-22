package com.tdns.toks.core.domain.quizrank.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdns.toks.core.domain.quizrank.model.dto.QuizRankDTO;
import com.tdns.toks.core.domain.quizrank.repository.QuizRankRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizRankService {
	private final QuizRankRepository quizRankRepository;

	public List<QuizRankDTO> getByStudyId(final Long studyId) {
		var dto = quizRankRepository.retrieveByStudyId(studyId);
		return calculateRank(dto);
	}

	private List<QuizRankDTO> calculateRank(final List<QuizRankDTO> dtos) {
		int rank = 1;
		int prevScore = 0;

		for (QuizRankDTO dto : dtos) {
			if (dto.getScore() == null) {
				break;
			}
			if (dto.getScore() < prevScore) {
				rank++;
			}
			dto.updateRank(rank);
			prevScore = dto.getScore();
		}
		return dtos;
	}
}
