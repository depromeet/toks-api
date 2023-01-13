package com.tdns.toks.api.domain.quiz.service;

import com.tdns.toks.api.domain.quiz.model.mapper.QuizMapper;
import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;
import com.tdns.toks.core.domain.quiz.model.entity.Quiz;
import com.tdns.toks.core.domain.quiz.service.QuizLikeService;
import com.tdns.toks.core.domain.quiz.service.QuizService;
import com.tdns.toks.core.domain.study.service.StudyService;
import com.tdns.toks.core.domain.study.service.StudyUserService;
import com.tdns.toks.core.domain.user.model.dto.UserDetailDTO;
import com.tdns.toks.core.domain.user.model.dto.UserSimpleByQuizIdDTO;
import com.tdns.toks.core.domain.user.model.dto.UserSimpleDTO;
import com.tdns.toks.core.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.tdns.toks.api.domain.quiz.model.dto.QuizApiDTO.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class QuizApiService {
    private final QuizService quizService;
    private final StudyUserService studyUserService;
    private final StudyService studyService;
    private final UserService userService;
    private final QuizLikeService quizLikeService;

    private final QuizMapper mapper;

    public QuizSimpleResponse getById(final Long id) {
        var quiz = quizService.retrieveByIdOrThrow(id);
        return QuizSimpleResponse.toResponse(quiz);
    }

    // TODO : 조회 로직 개선 필요
    public QuizzesResponse getAllByStudyId(final Long studyId) {
        var unSubmitters = studyUserService.filterUnSubmitterByStudyId(studyId);
        var quizzes = quizService.retrieveByStudyId(studyId);
        var userDTO = UserDetailDTO.get();

        //DB 한번 더 조회 하는게 걸리는데 UserDTO 에서 UserSimpleDTO 만드는 방법 떠오르지 않음..
        var user = userService.getUser(userDTO.getId());
        UserSimpleDTO userSimpleDTO = UserSimpleDTO.toDto(user);


        var results = quizzes.stream()
                .map(quiz -> {
                    var unSubmitter = new ArrayList<>(unSubmitters.stream()
                            .filter(userSimpleByQuizIdDTO -> userSimpleByQuizIdDTO.getQuizId().equals(quiz.getQuizId()))
                            .findFirst()
                            .map(UserSimpleByQuizIdDTO::getUsers)
                            .orElseGet(Collections::emptyList));
                    // 퀴즈 출제자는 퀴즈 답변을 출제할 수 없으므로, 미제출자에서 제외
                    unSubmitter.remove(quiz.getCreator());

                    //미제출자에 없다면? -> 풀었다 (true)
                    boolean isSolved = !unSubmitter.contains(userSimpleDTO);
                    boolean voted = quizLikeService.isVoted(user.getId(), quiz.getQuizId());

                    String quizSolveStep = "NONE";
                    if (isSolved) {
                        if (voted) {
                            quizSolveStep = "VOTED";
                        } else {
                            quizSolveStep = "SOLVED";
                        }
                    }

                    return QuizResponse.toResponse(
                            quiz,
                            unSubmitter,
                            quizService.getQuizStatus(quiz.getStartedAt(), quiz.getEndedAt()),
                            Objects.equals(quiz.getCreator().getUserId(), userDTO.getId()),
                            quizSolveStep);
                })
                .collect(Collectors.toList());

        return new QuizzesResponse(results);
    }

    public QuizCreateResponse create(QuizRequest request) {
        var userDTO = UserDetailDTO.get();
        var study = studyService.getStudy(request.getStudyId());

//        quizService.checkDuplicatedRound(request.getStudyId(), request.getRound());

//시은 마지막 퀴즈 검증
        Quiz latestQuiz = quizService.getStudyLatestQuiz(request.getStudyId());
        if (latestQuiz.getId() != -1) {
            if (latestQuiz.getRound() >= request.getRound()) {
                throw new SilentApplicationErrorException(ApplicationErrorType.ALREADY_EXISTS_QUIZ_ROUND);
            }

            if (latestQuiz.getEndedAt().isAfter(LocalDateTime.now())) {
                throw new SilentApplicationErrorException(ApplicationErrorType.STILL_OPEN_LATEST_QUIZ);
            }
        }
//시은 마지막 퀴즈 검증 끝


        var quiz = mapper.toEntity(request);
        var savedQuiz = quizService.save(quiz);

        study.updateLatestQuizRound();

        log.info("create quiz uid : {} / quizId : {}", userDTO.getId(), quiz.getId());

        return QuizCreateResponse.toResponse(savedQuiz);
    }
}
