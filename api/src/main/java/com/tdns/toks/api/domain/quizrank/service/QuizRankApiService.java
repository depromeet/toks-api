package com.tdns.toks.api.domain.quizrank.service;

import com.tdns.toks.core.domain.quizrank.service.QuizRankService;
import com.tdns.toks.core.domain.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.tdns.toks.api.domain.quizrank.model.dto.QuizRankApiDTO.QuizRanksApiResponse;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizRankApiService {

    private final StudyService studyService;
    private final QuizRankService quizRankService;

    @Transactional(readOnly = true)
    public QuizRanksApiResponse getByStudyId(final Long studyId) {
        studyService.isExists(studyId);
        var quizRanks = quizRankService.getByStudyId(studyId);

        return new QuizRanksApiResponse(quizRanks);
    }
}
