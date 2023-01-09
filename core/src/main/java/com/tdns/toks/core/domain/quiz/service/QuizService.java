package com.tdns.toks.core.domain.quiz.service;

import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.quiz.model.dto.QuizSimpleDTO;
import com.tdns.toks.core.domain.quiz.model.entity.Quiz;
import com.tdns.toks.core.domain.quiz.repository.QuizReplyHistoryRepository;
import com.tdns.toks.core.domain.quiz.repository.QuizRepository;
import com.tdns.toks.core.domain.quiz.type.QuizStatusType;
import com.tdns.toks.core.domain.quiz.type.StudyLatestQuizStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuizReplyHistoryRepository quizReplyHistoryRepository;

    public Quiz getOrThrow(final Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.NOT_FOUND_QUIZ_ERROR));
    }

    public QuizSimpleDTO retrieveByIdOrThrow(final Long id) {
        return quizRepository.retrieveById(id)
                .orElseThrow(() -> new SilentApplicationErrorException(ApplicationErrorType.NOT_FOUND_QUIZ_ERROR));
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

    public Quiz getStudyLatestQuiz(Long studyId) {
        return quizRepository.findFirstByStudyIdOrderByCreatedAtDesc(studyId)
                .orElse(Quiz.builder().id(-1L).build()); // 퀴즈가 없는 경우 id = -1인 Quiz 반환
    }

    public StudyLatestQuizStatus getStudyLatestQuizStatus(Quiz quiz, Long userId) {
        QuizStatusType quizStatus = getQuizStatus(quiz.getStartedAt(), quiz.getEndedAt());
        // 퀴즈 진행 중
        if (quizStatus.equals(QuizStatusType.IN_PROGRESS)) {
            if (quizReplyHistoryRepository.existsByQuizIdAndCreatedBy(quiz.getId(), userId)) { // 푼 경우
                return StudyLatestQuizStatus.SOLVED;
            }
            return StudyLatestQuizStatus.UNSOLVED;
        }
        // 퀴즈가 진행중이지 않은 경우
        LocalDateTime endNoticeTime = quiz.getEndedAt().plusHours(1);
        LocalDateTime nowTime = LocalDateTime.now();
        if (nowTime.isAfter(quiz.getEndedAt()) && nowTime.isBefore(endNoticeTime)) { // 퀴즈 끝나는 시간 < 현재시간 < 퀴즈 끝나고 1시간 뒤
            return StudyLatestQuizStatus.UNCHECKED;
        }
        return StudyLatestQuizStatus.PENDING;
    }

    public Quiz save(final Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public void checkDuplicatedRound(final Long studyId, final Integer round) {
        if (quizRepository.existsByStudyIdAndRound(studyId, round)) {
            throw new SilentApplicationErrorException(ApplicationErrorType.ALREADY_EXISTS_QUIZ_ROUND);
        }
    }
}
