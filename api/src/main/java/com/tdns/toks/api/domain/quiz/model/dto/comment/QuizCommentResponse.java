package com.tdns.toks.api.domain.quiz.model.dto.comment;

import com.tdns.toks.core.domain.quizcomment.model.entity.QuizComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizCommentResponse {
    private Long id;
    private Long quizId;
    private Long uid;
    private String content;
    private LocalDateTime createdAt;

    public static QuizCommentResponse from(QuizComment comment) {
        return new QuizCommentResponse(
                comment.getId(),
                comment.getQuizId(),
                comment.getUid(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}
