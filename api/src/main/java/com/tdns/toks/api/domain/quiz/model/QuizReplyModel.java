package com.tdns.toks.api.domain.quiz.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tdns.toks.core.domain.quiz.entity.QuizReplyHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizReplyModel {
    private Long id;
    private String answer;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public static QuizReplyModel from(QuizReplyHistory reply) {
        return new QuizReplyModel(
                reply.getId(),
                reply.getAnswer(),
                reply.getCreatedAt()
        );
    }
}
