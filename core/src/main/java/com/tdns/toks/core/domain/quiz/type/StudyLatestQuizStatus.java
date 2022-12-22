package com.tdns.toks.core.domain.quiz.type;

public enum StudyLatestQuizStatus {
    PENDING,    // 퀴즈 시작 전, 퀴즈가 없는 경우
    SOLVED,     // 퀴즈 진행 중, 문제 풀기 전
    UNSOLVED,   // 퀴즈 진행 중, 문제 품
    CHECKED,    // 퀴즈 종료, 우수 답변 확인 한 경우
    UNCHECKED   // 퀴즈 종료, 우수 답변 확인하지 않은 경우
}
