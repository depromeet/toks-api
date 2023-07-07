package com.tdns.toks.api.domain.admin.dto;

import com.tdns.toks.core.common.utils.MapperUtil;
import com.tdns.toks.core.domain.quiz.model.entity.Quiz;
import com.tdns.toks.core.domain.quiz.type.QuizType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminQuizResponse {
    private Long id;
    private String title;
    private String categoryId;
    private Map<String, Object> question;
    private QuizType quizType;
    private String description;
    private String answer;
    private Long createdBy;

    public static AdminQuizResponse from(Quiz quiz) {
        return new AdminQuizResponse(
                quiz.getId(),
                quiz.getTitle(),
                quiz.getCategoryId(),
                MapperUtil.readValue(quiz.getQuestion(), Map.class),
                quiz.getQuizType(),
                quiz.getDescription(),
                quiz.getAnswer(),
                quiz.getCreatedBy()
        );
    }
}
