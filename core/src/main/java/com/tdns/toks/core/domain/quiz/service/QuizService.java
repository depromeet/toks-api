package com.tdns.toks.core.domain.quiz.service;

import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.quiz.model.dto.QuizDTO.LatestQuizSimpleDto;
import com.tdns.toks.core.domain.quiz.model.dto.QuizSimpleDTO;
import com.tdns.toks.core.domain.quiz.model.entity.Quiz;
import com.tdns.toks.core.domain.quiz.repository.QuizReplyReplyHistoryRepository;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import com.tdns.toks.core.domain.quiz.type.QuizStatusType;
import com.tdns.toks.core.domain.quiz.type.StudyLatestQuizStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuizReplyReplyHistoryRepository quizReplyReplyHistoryRepository;

    public Quiz getOrThrow(final Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.INVALID_REQUEST));
    }

    public QuizSimpleDTO retrieveByIdOrThrow(final Long id) {
        return quizRepository.retrieveById(id)
                .orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.INVALID_REQUEST));
    }

    public List<QuizSimpleDTO> retrieveByStudyId(final Long studyId) {
        return quizRepository.retrieveByStudyId(studyId);
    }

    public QuizStatusType getQuizStatus(final LocalDateTime startedAt, final LocalDateTime endedAt) {
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(startedAt)) {
            return QuizStatusType.TO_DO;
        } else if (now.isAfter(endedAt)) {
            return QuizStatusType.DONE;
        }
        return QuizStatusType.IN_PROGRESS;
    }

    public LatestQuizSimpleDto getLatestQuizStatus(Long studyId, Long userId) {
        Optional<Quiz> quiz = quizRepository.findFirstByStudyIdOrderByCreatedAtDesc(studyId);
        if (quiz.isEmpty()) {
            return new LatestQuizSimpleDto(StudyLatestQuizStatus.CHECKED, -1L);
        }
        Quiz latestQuiz = quiz.get();
        QuizStatusType quizStatus = getQuizStatus(latestQuiz.getStartedAt(), latestQuiz.getEndedAt());
        if (quizStatus.equals(QuizStatusType.TO_DO)) {
            return new LatestQuizSimpleDto(StudyLatestQuizStatus.PENDING, latestQuiz.getId());
        } else if (quizStatus.equals(QuizStatusType.IN_PROGRESS)) {
            quizRepository.findById(latestQuiz.getId());
            if (quizReplyReplyHistoryRepository.existsByQuizIdAndCreatedBy(latestQuiz.getId(), userId)) {// 풀었나 안풀었나 판단
                return new LatestQuizSimpleDto(StudyLatestQuizStatus.SOLVED, latestQuiz.getId());
            }
            return new LatestQuizSimpleDto(StudyLatestQuizStatus.UNSOLVED, latestQuiz.getId());
        }
        LocalDateTime endAt = latestQuiz.getEndedAt().plusHours(1);
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(endAt)) {
            return new LatestQuizSimpleDto(StudyLatestQuizStatus.UNCHECKED, latestQuiz.getId());
        }
        return new LatestQuizSimpleDto(StudyLatestQuizStatus.CHECKED, latestQuiz.getId());
    }
}
