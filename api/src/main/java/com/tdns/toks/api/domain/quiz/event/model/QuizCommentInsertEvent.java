package com.tdns.toks.api.domain.quiz.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizCommentInsertEvent {
    private Long quizId;
}
