package com.tdns.toks.core.domain.quiz.type;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public enum QuizType {
    // AB 기본
    A_B_SIMPLE,
    // AB Image
    A_B_IMAGE,
    // OX 기본
    O_X_SIMPLE,
    // OX Image
    O_X_IMAGE,
    ;

    public static Set<String> getAnswer(QuizType type) {
        switch (type) {
            case A_B_SIMPLE:
            case A_B_IMAGE:
                return Set.of("A", "B");

            case O_X_SIMPLE:
            case O_X_IMAGE:
                return Set.of("O", "X");

            default:
                return new HashSet<>();
        }
    }
}
