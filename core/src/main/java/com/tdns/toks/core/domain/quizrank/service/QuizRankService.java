package com.tdns.toks.core.domain.quizrank.service;

import com.tdns.toks.core.domain.quizrank.model.dto.QuizRankDTO;
import com.tdns.toks.core.domain.quizrank.model.entity.QuizRank;
import com.tdns.toks.core.domain.quizrank.repository.QuizRankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizRankService {
    private final QuizRankRepository quizRankRepository;

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

    public QuizRank save(final Long userId, final Long studyId) {
        return quizRankRepository.save(QuizRank.of(0, userId, studyId));
    }
}
