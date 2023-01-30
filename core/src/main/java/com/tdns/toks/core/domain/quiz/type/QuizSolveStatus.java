package com.tdns.toks.core.domain.quiz.type;

import lombok.Getter;

@Getter
public enum QuizSolveStatus {
    NONE,
    VOTED,
    SOLVED,
    ;

    public static QuizSolveStatus of(Boolean isSolved, Boolean isVoted) {
        if (isSolved && isVoted) {
            return VOTED;
        }

        if (isSolved) {
            return SOLVED;
        }

        return NONE;
    }
}
