package com.tdns.toks.api.domain.quiz.model.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizCommentCreateRequest {
    private String comment;
}
