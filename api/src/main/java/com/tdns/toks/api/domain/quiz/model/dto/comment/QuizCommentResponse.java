package com.tdns.toks.api.domain.quiz.model.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tdns.toks.core.domain.quizcomment.model.entity.QuizComment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class QuizCommentResponse {
    private final Long id;
    private final Long quizId;
    private final Long uid;
    private final String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "Asia/Seoul")
    private final LocalDateTime createdAt;

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
