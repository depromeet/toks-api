package com.tdns.toks.api.domain.quiz.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tdns.toks.core.domain.quiz.model.dto.QuizSimpleDTO;
import com.tdns.toks.core.domain.quiz.model.entity.Quiz;
import com.tdns.toks.core.domain.quiz.type.QuizStatusType;
import com.tdns.toks.core.domain.quiz.type.QuizType;
import com.tdns.toks.core.domain.user.model.dto.UserSimpleDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class QuizApiDTO {
    @Getter
    @RequiredArgsConstructor
    @Schema(name = "QuizSimpleResponse", description = "QUIZ 단일 조회 응답 모델")
    public static class QuizSimpleResponse {
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quizId", description = "quiz id")
        private final Long quizId;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "question", description = "question")
        private final String question;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "answer", description = "answer")
        private final String answer;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quizType", description = "quiz type")
        private final QuizType quizType;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "description", description = "description")
        private final String description;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "startedAt", description = "started at")
        private final LocalDateTime startedAt;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "endedAt", description = "ended at")
        private final LocalDateTime endedAt;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "durationOfSecond", description = "duration of second")
        private final Long durationOfSecond;

        @JsonFormat(timezone = "Asia/Seoul")
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "timestamp", description = "timestamp")
        private final Timestamp timestamp;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "imageUrls", description = "image urls")
        private final List<String> imageUrls;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "creator", description = "creator")
        private final UserSimpleDTO creator;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "studyId", description = "study id")
        private final Long studyId;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "round", description = "round")
        private final Integer round;

        public static QuizSimpleResponse toResponse(QuizSimpleDTO quizSimpleDTO) {
            var durationOfSecond = Duration.between(quizSimpleDTO.getStartedAt(), quizSimpleDTO.getEndedAt()).getSeconds();
            var timeStamp = new Timestamp(System.currentTimeMillis());

            return new QuizSimpleResponse(
                    quizSimpleDTO.getQuizId(),
                    quizSimpleDTO.getQuestion(),
                    quizSimpleDTO.getAnswer(),
                    quizSimpleDTO.getQuizType(),
                    quizSimpleDTO.getDescription(),
                    quizSimpleDTO.getStartedAt(),
                    quizSimpleDTO.getEndedAt(),
                    durationOfSecond,
                    timeStamp,
                    quizSimpleDTO.getImageUrls(),
                    quizSimpleDTO.getCreator(),
                    quizSimpleDTO.getStudyId(),
                    quizSimpleDTO.getRound()
            );
        }
    }

    @Getter
    @RequiredArgsConstructor
    @Schema(name = "QuizResponse", description = "QUIZ 조회 응답 모델")
    public static class QuizResponse {
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quizId", description = "quiz id")
        private final Long quizId;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "question", description = "question")
        private final String question;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quizType", description = "quiz type")
        private final QuizType quizType;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "description", description = "description")
        private final String description;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "startedAt", description = "started at")
        private final LocalDateTime startedAt;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "endedAt", description = "ended at")
        private final LocalDateTime endedAt;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "durationOfSecond", description = "duration of second")
        private final Long durationOfSecond;

        @JsonFormat(timezone = "Asia/Seoul")
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "timestamp", description = "timestamp")
        private final Timestamp timestamp;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "creator", description = "creator")
        private final UserSimpleDTO creator;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "unSubmitters", description = "unSubmitters")
        private final List<UserSimpleDTO> unSubmitters;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "studyId", description = "study id")
        private final Long studyId;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quizStatus", description = "study id")
        private final QuizStatusType quizStatus;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "round", description = "round")
        private final Integer round;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "isMyQuiz", description = "퀴즈 생성자 여부")
        private final boolean isMyQuiz;

        // 시은 풀었는지 안풀었는지 확인 필드
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "isSolved", description = "퀴즈 풀이 여부")
        private final boolean isSolved;

        public static QuizResponse toResponse(QuizSimpleDTO quizSimpleDTO, List<UserSimpleDTO> unSubmitter, QuizStatusType quizStatus, boolean isMyQuiz, boolean isSolved) {
            return new QuizResponse(
                    quizSimpleDTO.getQuizId(),
                    quizSimpleDTO.getQuestion(),
                    quizSimpleDTO.getQuizType(),
                    quizSimpleDTO.getDescription(),
                    quizSimpleDTO.getStartedAt(),
                    quizSimpleDTO.getEndedAt(),
                    Duration.between(quizSimpleDTO.getStartedAt(), quizSimpleDTO.getEndedAt()).getSeconds(),
                    new Timestamp(System.currentTimeMillis()),
                    quizSimpleDTO.getCreator(),
                    unSubmitter,
                    quizSimpleDTO.getStudyId(),
                    quizStatus,
                    quizSimpleDTO.getRound(),
                    isMyQuiz,
                    isSolved
            );
        }
    }

    @Getter
    @RequiredArgsConstructor
    @Schema(name = "QuizzesResponse", description = "QUIZ 조회 응답 모델")
    public static class QuizzesResponse {
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quizzes", description = "quizzes 리스트")
        private final List<QuizResponse> quizzes;
    }

    @Data
    @Schema(name = "QuizRequest", description = "QUIZ REPLY HISTORY 생성 요청 모델")
    public static class QuizRequest {
        @NotNull(message = "퀴즈 답변은 필수 항목입니다.")
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "question", description = "question")
        private String question;

        @NotNull(message = "퀴즈 타입은 필수 항목입니다.")
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quizType", description = "quizType")
        private QuizType quizType;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, name = "description", description = "description")
        private String description;

        @NotNull(message = "퀴즈 답변은 필수 항목입니다.")
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "answer", description = "answer")
        private String answer;

        @NotNull(message = "퀴즈 시작시간은 필수 항목입니다.")
//		@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "Asia/Seoul")
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "startedAt", description = "startedAt")
        private LocalDateTime startedAt;

        @NotNull(message = "퀴즈 종료시간은 필수 항목입니다.")
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "durationOfSecond", description = "durationOfSecond")
        private Long durationOfSecond;

        @NotNull(message = "study id는 필수 항목입니다.")
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "studyId", description = "studyId")
        private Long studyId;

        @NotNull(message = "round는 필수 항목입니다.")
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "round", description = "round")
        private Integer round;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, name = "imageUrls", description = "imageUrls")
        private List<String> imageUrls;
    }

    @Getter
    @RequiredArgsConstructor
    @Schema(name = "QuizCreateResponse", description = "QUIZ 생성 응답 모델")
    public static class QuizCreateResponse {
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quiz id", description = "quizId")
        private final Long quizId;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "question", description = "question")
        private final String question;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "quizType", description = "quizType")
        private final QuizType quizType;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "description", description = "description")
        private final String description;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "startedAt", description = "startedAt")
        private final LocalDateTime startedAt;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "endedAt", description = "endedAt")
        private final LocalDateTime endedAt;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "imageUrls", description = "imageUrls")
        private final List<String> imageUrls;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "study id", description = "study id")
        private final Long studyId;

        @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true, name = "round", description = "round")
        private final Integer round;

        public static QuizCreateResponse toResponse(Quiz quiz) {
            return new QuizCreateResponse(
                    quiz.getId(),
                    quiz.getQuestion(),
                    quiz.getQuizType(),
                    quiz.getDescription(),
                    quiz.getStartedAt(),
                    quiz.getEndedAt(),
                    quiz.getImageUrls(),
                    quiz.getStudyId(),
                    quiz.getRound()
            );
        }
    }
}
