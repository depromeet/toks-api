package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.domain.quiz.model.mapper.QuizMapper;
import com.tdns.toks.core.domain.quiz.service.QuizService;
import com.tdns.toks.core.domain.study.service.StudyService;
import com.tdns.toks.core.domain.study.service.StudyUserService;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import com.tdns.toks.core.domain.user.model.dto.UserSimpleByQuizIdDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.tdns.toks.api.domain.quiz.model.dto.QuizApiDTO.QuizCreateResponse;
import static com.tdns.toks.api.domain.quiz.model.dto.QuizApiDTO.QuizRequest;
import static com.tdns.toks.api.domain.quiz.model.dto.QuizApiDTO.QuizResponse;
import static com.tdns.toks.api.domain.quiz.model.dto.QuizApiDTO.QuizSimpleResponse;
import static com.tdns.toks.api.domain.quiz.model.dto.QuizApiDTO.QuizzesResponse;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class QuizApiService {
    private final QuizService quizService;
    private final StudyUserService studyUserService;
    private final StudyService studyService;

    private final QuizMapper mapper;

    public QuizSimpleResponse getById(final Long id) {
        var quiz = quizService.retrieveByIdOrThrow(id);
        return QuizSimpleResponse.toResponse(quiz);
    }

    public QuizzesResponse getAllByStudyId(final Long studyId) {
        var unSubmitters = studyUserService.filterUnSubmitterByStudyId(studyId);
        var quizzes = quizService.retrieveByStudyId(studyId);
        var userDTO = UserDetailDTO.get();

        var results = quizzes.stream()
                .map(quiz -> {
                    var unSubmitter = new ArrayList<>(unSubmitters.stream()
                            .filter(userSimpleByQuizIdDTO -> userSimpleByQuizIdDTO.getQuizId().equals(quiz.getQuizId()))
                            .findFirst()
                            .map(UserSimpleByQuizIdDTO::getUsers)
                            .orElseGet(Collections::emptyList));
                    // 퀴즈 출제자는 퀴즈 답변을 출제할 수 없으므로, 미제출자에서 제외
                    unSubmitter.remove(quiz.getCreator());

                    return QuizResponse.toResponse(
                            quiz,
                            unSubmitter,
                            quizService.getQuizStatus(quiz.getStartedAt(), quiz.getEndedAt()),
                            Objects.equals(quiz.getCreator().getUserId(), userDTO.getId()));
                })
                .collect(Collectors.toList());

        return new QuizzesResponse(results);
    }

    public QuizCreateResponse create(QuizRequest request) {
        // 사용자 조회
        var userDTO = UserDetailDTO.get();
        // 스터디 조회
        var study = studyService.getStudy(request.getStudyId());

        // 어떤 스터디에 라운드가 중복되었는지
        quizService.checkDuplicatedRound(request.getStudyId(), request.getRound());

        var quiz = mapper.toEntity(request);
        var savedQuiz = quizService.save(quiz);

        // 라운드 하나 올려주기
        study.updateLatestQuizRound();

        log.info("create quiz uid : {} / quizId : {}", userDTO.getId(), quiz.getId());

        return QuizCreateResponse.toResponse(savedQuiz);
    }
}
