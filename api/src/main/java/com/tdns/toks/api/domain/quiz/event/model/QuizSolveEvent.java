package com.tdns.toks.api.domain.quiz.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizSolveEvent {
    private Long quizId;
}
