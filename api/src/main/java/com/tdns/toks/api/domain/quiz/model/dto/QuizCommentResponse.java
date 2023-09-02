package com.tdns.toks.api.domain.quiz.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tdns.toks.core.domain.quizcomment.entity.QuizComment;
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
    private Integer likeCount;
    private String nickname;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private String profileImageUrl;
    private Boolean isLiked;

    public static QuizCommentResponse from(QuizComment comment, String nickname, int likeCount, String profileImageUrl, Boolean isLiked) {
        return new QuizCommentResponse(
                comment.getId(),
                comment.getQuizId(),
                comment.getUid(),
                likeCount,
                nickname,
                comment.getContent(),
                comment.getCreatedAt(),
                profileImageUrl,
                isLiked
        );
    }
}
