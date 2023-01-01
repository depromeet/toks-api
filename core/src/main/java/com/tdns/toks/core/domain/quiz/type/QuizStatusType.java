package com.tdns.toks.core.domain.quiz.type;

import lombok.Getter;

@Getter
public enum QuizStatusType {

	TO_DO,			// 퀴즈 시작 전
	IN_PROGRESS,	// 퀴즈 진행 중
	DONE			// 퀴즈 종료
	;
}
