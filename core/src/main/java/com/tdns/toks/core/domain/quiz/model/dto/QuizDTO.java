package com.tdns.toks.core.domain.quiz.model.dto;

import com.tdns.toks.core.domain.quiz.type.StudyLatestQuizStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class QuizDTO {

    @Getter
    @AllArgsConstructor
    public static class LatestQuizSimpleDto{
        private StudyLatestQuizStatus studyLatestQuizStatus;
        private Long quizId;
    }
}
