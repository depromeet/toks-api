package com.tdns.toks.api.domain.quiz.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tdns.toks.core.domain.quizcomment.entity.QuizComment;
import com.tdns.toks.core.domain.user.entity.User;
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

    public static QuizCommentResponse from(
            QuizComment comment,
            User user,
            int likeCount,
            Boolean isLiked
    ) {
        return new QuizCommentResponse(
                comment.getId(),
                comment.getQuizId(),
                comment.getUid(),
                likeCount,
                resolveNickname(user),
                comment.getContent(),
                comment.getCreatedAt(),
                resolveProfileImageUrl(user),
                isLiked
        );
    }

    private static String resolveNickname(User user) {
        // default nickname
        if (user == null) {
            return "똑스";
        }
        return user.getNickname();
    }

    private static String resolveProfileImageUrl(User user) {
        // default image
        if (user == null) {
            return "https://toks-web-assets.s3.amazonaws.com/emoji/ic_drooling.svg";
        }
        return user.getNickname();
    }
}
